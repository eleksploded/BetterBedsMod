package net.minecraft.client;

import com.google.common.collect.Queues;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.LoadingGui;
import net.minecraft.client.gui.ResourceLoadProgressGui;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.fonts.FontResourceManager;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ConnectingScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MemoryErrorScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SleepInMultiplayerScreen;
import net.minecraft.client.gui.screen.WinGameScreen;
import net.minecraft.client.gui.screen.WorkingScreen;
import net.minecraft.client.gui.screen.WorldLoadProgressScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.login.ClientLoginNetHandler;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.FirstPersonRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.GlDebugTextUtils;
import net.minecraft.client.renderer.IWindowEventListener;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.ScreenSize;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VirtualScreen;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkRender;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.PaintingSpriteUploader;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.ClientResourcePackInfo;
import net.minecraft.client.resources.DownloadingPackFinder;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.LegacyResourcePackWrapper;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.client.settings.AmbientOcclusionStatus;
import net.minecraft.client.settings.CloudOption;
import net.minecraft.client.settings.CreativeSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.client.util.IMutableSearchTree;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.SearchTree;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.client.util.SearchTreeReloadable;
import net.minecraft.client.util.Splashes;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.item.LeashKnotEntity;
import net.minecraft.entity.item.PaintingEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.ProtocolType;
import net.minecraft.network.handshake.client.CHandshakePacket;
import net.minecraft.network.login.client.CLoginStartPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.profiler.DataPoint;
import net.minecraft.profiler.DebugProfiler;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.IProfiler;
import net.minecraft.profiler.ISnooperInfo;
import net.minecraft.profiler.Snooper;
import net.minecraft.resources.FolderPackFinder;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DefaultUncaughtExceptionHandler;
import net.minecraft.util.Direction;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Timer;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.concurrent.RecursiveEventLoop;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.registry.Bootstrap;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.listener.ChainedChunkStatusListener;
import net.minecraft.world.chunk.listener.TrackingChunkStatusListener;
import net.minecraft.world.dimension.EndDimension;
import net.minecraft.world.dimension.NetherDimension;
import net.minecraft.world.storage.SaveFormat;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class Minecraft extends RecursiveEventLoop<Runnable> implements ISnooperInfo, IWindowEventListener, AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final boolean IS_RUNNING_ON_MAC = Util.getOSType() == Util.OS.OSX;
   public static final ResourceLocation DEFAULT_FONT_RENDERER_NAME = new ResourceLocation("default");
   public static final ResourceLocation standardGalacticFontRenderer = new ResourceLocation("alt");
   public static CompletableFuture<Unit> field_213278_d = CompletableFuture.completedFuture(Unit.INSTANCE);
   public static byte[] memoryReserve = new byte[10485760];
   private static int cachedMaximumTextureSize = -1;
   private final File fileResourcepacks;
   private final PropertyMap profileProperties;
   private final ScreenSize field_195556_O;
   private ServerData currentServerData;
   public TextureManager textureManager;
   private static Minecraft instance;
   private final DataFixer dataFixer;
   public PlayerController field_71442_b;
   private VirtualScreen virtualScreen;
   public MainWindow mainWindow;
   private boolean hasCrashed;
   private CrashReport crashReporter;
   private boolean connectedToRealms;
   private final Timer timer = new Timer(20.0F, 0L);
   private final Snooper snooper = new Snooper("client", this, Util.milliTime());
   public ClientWorld world;
   public WorldRenderer worldRenderer;
   private EntityRendererManager field_175616_W;
   private ItemRenderer itemRenderer;
   private FirstPersonRenderer firstPersonRenderer;
   public ClientPlayerEntity player;
   @Nullable
   public Entity renderViewEntity;
   @Nullable
   public Entity pointedEntity;
   public ParticleManager particles;
   private final SearchTreeManager searchTreeManager = new SearchTreeManager();
   private final Session session;
   private boolean isGamePaused;
   private float renderPartialTicksPaused;
   public FontRenderer fontRenderer;
   @Nullable
   public Screen field_71462_r;
   @Nullable
   public LoadingGui field_213279_p;
   public GameRenderer gameRenderer;
   public DebugRenderer debugRenderer;
   protected int leftClickCounter;
   @Nullable
   private IntegratedServer integratedServer;
   private final AtomicReference<TrackingChunkStatusListener> field_213277_ad = new AtomicReference<>();
   public IngameGui field_71456_v;
   public boolean skipRenderWorld;
   public RayTraceResult objectMouseOver;
   public GameSettings gameSettings;
   private CreativeSettings creativeSettings;
   public MouseHelper mouseHelper;
   public KeyboardListener keyboardListener;
   public final File gameDir;
   private final File fileAssets;
   private final String launchedVersion;
   private final String versionType;
   private final Proxy proxy;
   private SaveFormat field_71469_aa;
   private static int debugFPS;
   private int rightClickDelayTimer;
   private String serverName;
   private int serverPort;
   public final FrameTimer frameTimer = new FrameTimer();
   private long startNanoTime = Util.nanoTime();
   private final boolean jvm64bit;
   private final boolean isDemo;
   @Nullable
   private NetworkManager networkManager;
   private boolean integratedServerIsRunning;
   private final DebugProfiler field_71424_I = new DebugProfiler(() -> {
      return this.timer.elapsedTicks;
   });
   private IReloadableResourceManager resourceManager;
   private final DownloadingPackFinder packFinder;
   private final ResourcePackList<ClientResourcePackInfo> resourcePackRepository;
   private LanguageManager languageManager;
   private BlockColors blockColors;
   private ItemColors itemColors;
   private Framebuffer framebuffer;
   private AtlasTexture field_147128_au;
   private SoundHandler soundHandler;
   private MusicTicker musicTicker;
   private FontResourceManager fontResourceMananger;
   private Splashes field_213271_aF;
   private final MinecraftSessionService sessionService;
   private SkinManager skinManager;
   private final Thread thread = Thread.currentThread();
   private ModelManager modelManager;
   private BlockRendererDispatcher blockRenderDispatcher;
   private PaintingSpriteUploader field_213272_aL;
   private PotionSpriteUploader field_213273_aM;
   private final ToastGui field_193034_aS;
   private final MinecraftGame field_213274_aO = new MinecraftGame(this);
   private volatile boolean running = true;
   public String debug = "";
   public boolean renderChunksMany = true;
   private long debugUpdateTime;
   private int fpsCounter;
   private final Tutorial tutorial;
   private boolean isWindowFocused;
   private final Queue<Runnable> field_213275_aU = Queues.newConcurrentLinkedQueue();
   private CompletableFuture<Void> field_213276_aV;
   private String debugProfilerName = "root";

   public Minecraft(GameConfiguration gameConfig) {
      super("Client");
      this.field_195556_O = gameConfig.field_178743_b;
      instance = this;
      net.minecraftforge.client.ForgeHooksClient.invalidateLog4jThreadCache();
      this.gameDir = gameConfig.field_178744_c.gameDir;
      this.fileAssets = gameConfig.field_178744_c.assetsDir;
      this.fileResourcepacks = gameConfig.field_178744_c.resourcePacksDir;
      this.launchedVersion = gameConfig.field_178741_d.version;
      this.versionType = gameConfig.field_178741_d.versionType;
      this.profileProperties = gameConfig.field_178745_a.profileProperties;
      this.packFinder = new DownloadingPackFinder(new File(this.gameDir, "server-resource-packs"), gameConfig.field_178744_c.getAssetsIndex());
      this.resourcePackRepository = new ResourcePackList<>((p_213262_0_, p_213262_1_, p_213262_2_, p_213262_3_, p_213262_4_, p_213262_5_) -> {
         Supplier<IResourcePack> supplier;
         if (p_213262_4_.getPackFormat() < SharedConstants.getVersion().getPackVersion()) {
            supplier = () -> {
               return new LegacyResourcePackWrapper((IResourcePack)p_213262_2_.get(), LegacyResourcePackWrapper.NEW_TO_LEGACY_MAP);
            };
         } else {
            supplier = p_213262_2_;
         }

         return new ClientResourcePackInfo(p_213262_0_, p_213262_1_, supplier, p_213262_3_, p_213262_4_, p_213262_5_, p_213262_3_.isHidden());
      });
      this.resourcePackRepository.addPackFinder(this.packFinder);
      this.resourcePackRepository.addPackFinder(new FolderPackFinder(this.fileResourcepacks));
      this.proxy = gameConfig.field_178745_a.proxy == null ? Proxy.NO_PROXY : gameConfig.field_178745_a.proxy;
      this.sessionService = (new YggdrasilAuthenticationService(this.proxy, UUID.randomUUID().toString())).createMinecraftSessionService();
      this.session = gameConfig.field_178745_a.session;
      LOGGER.info("Setting user: {}", (Object)this.session.getUsername());
      this.isDemo = gameConfig.field_178741_d.isDemo;
      this.jvm64bit = isJvm64bit();
      this.integratedServer = null;
      if (gameConfig.field_178742_e.serverName != null) {
         this.serverName = gameConfig.field_178742_e.serverName;
         this.serverPort = gameConfig.field_178742_e.serverPort;
      }

      Bootstrap.register();
      Bootstrap.func_218821_c();
      KeybindTextComponent.displaySupplierFunction = KeyBinding::getDisplayString;
      this.dataFixer = DataFixesManager.getDataFixer();
      this.field_193034_aS = new ToastGui(this);
      this.tutorial = new Tutorial(this);
   }

   public void run() {
      this.running = true;

      try {
         this.init();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Initializing game");
         crashreport.makeCategory("Initialization");
         this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(crashreport));
         return;
      }

      try {
         try {
            boolean flag = false;

            while(this.running) {
               if (this.hasCrashed && this.crashReporter != null) {
                  this.displayCrashReport(this.crashReporter);
                  return;
               }

               try {
                  this.runGameLoop(!flag);
               } catch (OutOfMemoryError outofmemoryerror) {
                  if (flag) {
                     throw outofmemoryerror;
                  }

                  this.freeMemory();
                  this.displayGuiScreen(new MemoryErrorScreen());
                  System.gc();
                  LOGGER.fatal("Out of memory", (Throwable)outofmemoryerror);
                  flag = true;
               }
            }

            return;
         } catch (ReportedException reportedexception) {
            this.addGraphicsAndWorldToCrashReport(reportedexception.getCrashReport());
            this.freeMemory();
            LOGGER.fatal("Reported exception thrown!", (Throwable)reportedexception);
            this.displayCrashReport(reportedexception.getCrashReport());
         } catch (Throwable throwable1) {
            CrashReport crashreport1 = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", throwable1));
            LOGGER.fatal("Unreported exception thrown!", throwable1);
            this.freeMemory();
            this.displayCrashReport(crashreport1);
         }

      } finally {
         this.shutdownMinecraftApplet();
      }
   }

   /**
    * Starts the game: initializes the canvas, the title, the settings, etcetera.
    */
   private void init() {
      this.gameSettings = new GameSettings(this, this.gameDir);
      this.creativeSettings = new CreativeSettings(this.gameDir, this.dataFixer);
      this.startTimerHackThread();
      LOGGER.info("LWJGL Version: {}", (Object)GLX.getLWJGLVersion());
      ScreenSize screensize = this.field_195556_O;
      if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
         screensize = new ScreenSize(this.gameSettings.overrideWidth, this.gameSettings.overrideHeight, screensize.field_216496_c, screensize.field_216497_d, screensize.field_216498_e);
      }

      LongSupplier longsupplier = GLX.initGlfw();
      if (longsupplier != null) {
         Util.nanoTimeSupplier = longsupplier;
      }

      this.virtualScreen = new VirtualScreen(this);
      this.mainWindow = this.virtualScreen.func_217626_a(screensize, this.gameSettings.fullscreenResolution, "Minecraft " + SharedConstants.getVersion().getName());
      this.func_213228_a(true);

      try {
         InputStream inputstream = this.getPackFinder().getVanillaPack().getResourceStream(ResourcePackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_16x16.png"));
         InputStream inputstream1 = this.getPackFinder().getVanillaPack().getResourceStream(ResourcePackType.CLIENT_RESOURCES, new ResourceLocation("icons/icon_32x32.png"));
         this.mainWindow.func_216529_a(inputstream, inputstream1);
      } catch (IOException ioexception) {
         LOGGER.error("Couldn't set icon", (Throwable)ioexception);
      }

      this.mainWindow.func_216526_a(this.gameSettings.limitFramerate);
      this.mouseHelper = new MouseHelper(this);
      this.mouseHelper.registerCallbacks(this.mainWindow.getHandle());
      this.keyboardListener = new KeyboardListener(this);
      this.keyboardListener.setupCallbacks(this.mainWindow.getHandle());
      GLX.init();
      GlDebugTextUtils.setDebugVerbosity(this.gameSettings.glDebugVerbosity, false);
      this.framebuffer = new Framebuffer(this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight(), true, IS_RUNNING_ON_MAC);
      this.framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.resourceManager = new SimpleReloadableResourceManager(ResourcePackType.CLIENT_RESOURCES, this.thread);
      this.gameSettings.fillResourcePackList(this.resourcePackRepository);
      net.minecraftforge.fml.client.ClientModLoader.begin(this, this.resourcePackRepository, this.resourceManager, this.packFinder);
      this.resourcePackRepository.reloadPacksFromFinders();
      List<IResourcePack> list = this.resourcePackRepository.getEnabledPacks().stream().map(ResourcePackInfo::getResourcePack).collect(Collectors.toList());

      for(IResourcePack iresourcepack : list) {
         this.resourceManager.addResourcePack(iresourcepack);
      }

      this.languageManager = new LanguageManager(this.gameSettings.language);
      this.resourceManager.func_219534_a(this.languageManager);
      this.languageManager.parseLanguageMetadata(list);
      this.textureManager = new TextureManager(this.resourceManager);
      this.resourceManager.func_219534_a(this.textureManager);
      //net.minecraftforge.fml.client.SplashProgress.drawVanillaScreen(this.textureManager);
      this.func_213226_a();
      this.skinManager = new SkinManager(this.textureManager, new File(this.fileAssets, "skins"), this.sessionService);
      this.field_71469_aa = new SaveFormat(this.gameDir.toPath().resolve("saves"), this.gameDir.toPath().resolve("backups"), this.dataFixer);
      this.soundHandler = new SoundHandler(this.resourceManager, this.gameSettings);
      this.resourceManager.func_219534_a(this.soundHandler);
      this.field_213271_aF = new Splashes(this.session);
      this.resourceManager.func_219534_a(this.field_213271_aF);
      this.musicTicker = new MusicTicker(this);
      this.fontResourceMananger = new FontResourceManager(this.textureManager, this.getForceUnicodeFont());
      this.resourceManager.func_219534_a(this.fontResourceMananger.func_216884_a());
      this.fontRenderer = this.fontResourceMananger.getFontRenderer(DEFAULT_FONT_RENDERER_NAME);
      if (this.gameSettings.language != null) {
         this.fontRenderer.setBidiFlag(this.languageManager.isCurrentLanguageBidirectional());
      }

      this.resourceManager.func_219534_a(new GrassColorReloadListener());
      this.resourceManager.func_219534_a(new FoliageColorReloadListener());
      try (net.minecraftforge.fml.common.progress.ProgressBar bar = net.minecraftforge.fml.common.progress.StartupProgressManager.start("Rendering Setup", 5, true)) {
      bar.step("GL Setup");
      this.mainWindow.setRenderPhase("Startup");
      GlStateManager.enableTexture();
      GlStateManager.shadeModel(7425);
      GlStateManager.clearDepth(1.0D);
      GlStateManager.enableDepthTest();
      net.minecraftforge.fml.client.ClientModLoader.complete();
      GlStateManager.depthFunc(515);
      GlStateManager.enableAlphaTest();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.cullFace(GlStateManager.CullFace.BACK);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      this.mainWindow.setRenderPhase("Post startup");
      bar.step("Loading Texture Map");
      this.field_147128_au = new AtlasTexture("textures");
      this.field_147128_au.setMipmapLevels(this.gameSettings.mipmapLevels);
      this.textureManager.loadTickableTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE, this.field_147128_au);
      this.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
      this.field_147128_au.setBlurMipmapDirect(false, this.gameSettings.mipmapLevels > 0);
      bar.step("Loading Model Manager");
      this.modelManager = new ModelManager(this.field_147128_au);
      this.resourceManager.func_219534_a(this.modelManager);
      this.blockColors = BlockColors.init();
      this.itemColors = ItemColors.init(this.blockColors);
      bar.step("Loading Item Renderer");
      this.itemRenderer = new ItemRenderer(this.textureManager, this.modelManager, this.itemColors);
      this.field_175616_W = new EntityRendererManager(this.textureManager, this.itemRenderer, this.resourceManager);
      this.firstPersonRenderer = new FirstPersonRenderer(this);
      this.resourceManager.func_219534_a(this.itemRenderer);
      bar.step("Loading Entity Renderer");
      //net.minecraftforge.fml.client.SplashProgress.pause();
      this.gameRenderer = new GameRenderer(this, this.resourceManager);
      this.resourceManager.func_219534_a(this.gameRenderer);
      this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.blockColors);
      this.resourceManager.func_219534_a(this.blockRenderDispatcher);
      this.worldRenderer = new WorldRenderer(this);
      this.resourceManager.func_219534_a(this.worldRenderer);
      this.populateSearchTreeManager();
      this.resourceManager.func_219534_a(this.searchTreeManager);
      GlStateManager.viewport(0, 0, this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight());
      this.particles = new ParticleManager(this.world, this.textureManager);
      this.resourceManager.func_219534_a(this.particles);
      this.field_213272_aL = new PaintingSpriteUploader(this.textureManager);
      this.resourceManager.func_219534_a(this.field_213272_aL);
      this.field_213273_aM = new PotionSpriteUploader(this.textureManager);
      this.resourceManager.func_219534_a(this.field_213273_aM);
      //net.minecraftforge.fml.client.SplashProgress.resume();
      }; // Forge: end progress bar
      net.minecraftforge.fml.client.ClientModLoader.end();
      this.field_71456_v = new net.minecraftforge.client.ForgeIngameGui(this);
      this.debugRenderer = new DebugRenderer(this);
      GLX.setGlfwErrorCallback(this::disableVSyncAfterGlError);
      net.minecraftforge.fml.client.ClientModLoader.complete();
      if (this.gameSettings.fullScreen && !this.mainWindow.isFullscreen()) {
         this.mainWindow.toggleFullscreen();
         this.gameSettings.fullScreen = this.mainWindow.isFullscreen();
      }

      this.mainWindow.func_216523_b(this.gameSettings.enableVsync);
      this.mainWindow.setLogOnGlError();
      if (this.serverName != null) {
         this.displayGuiScreen(new ConnectingScreen(new MainMenuScreen(), this, this.serverName, this.serverPort));
      } else {
         this.displayGuiScreen(new MainMenuScreen(true));
      }

      ResourceLoadProgressGui.func_212970_a(this);
      this.func_213268_a(new ResourceLoadProgressGui(this, this.resourceManager.func_219535_a(Util.func_215072_e(), this, CompletableFuture.completedFuture(Unit.INSTANCE)), () -> {
         if (SharedConstants.developmentMode) {
            this.func_213256_aB();
         }

      }, false));
   }

   /**
    * Fills {@link #searchTreeManager} with the current item and recipe registry contents.
    */
   public void populateSearchTreeManager() {
      SearchTree<ItemStack> searchtree = new SearchTree<>((p_213242_0_) -> {
         return p_213242_0_.getTooltip((PlayerEntity)null, ITooltipFlag.TooltipFlags.NORMAL).stream().map((p_213230_0_) -> {
            return TextFormatting.getTextWithoutFormattingCodes(p_213230_0_.getString()).trim();
         }).filter((p_213267_0_) -> {
            return !p_213267_0_.isEmpty();
         });
      }, (p_213251_0_) -> {
         return Stream.of(Registry.field_212630_s.getKey(p_213251_0_.getItem()));
      });
      SearchTreeReloadable<ItemStack> searchtreereloadable = new SearchTreeReloadable<>((p_213235_0_) -> {
         return p_213235_0_.getItem().getTags().stream();
      });
      NonNullList<ItemStack> nonnulllist = NonNullList.create();

      for(Item item : Registry.field_212630_s) {
         item.fillItemGroup(ItemGroup.SEARCH, nonnulllist);
      }

      nonnulllist.forEach((p_213232_2_) -> {
         searchtree.func_217872_a(p_213232_2_);
         searchtreereloadable.func_217872_a(p_213232_2_);
      });
      SearchTree<RecipeList> searchtree1 = new SearchTree<>((p_213252_0_) -> {
         return p_213252_0_.getRecipes().stream().flatMap((p_213234_0_) -> {
            return p_213234_0_.getRecipeOutput().getTooltip((PlayerEntity)null, ITooltipFlag.TooltipFlags.NORMAL).stream();
         }).map((p_213264_0_) -> {
            return TextFormatting.getTextWithoutFormattingCodes(p_213264_0_.getString()).trim();
         }).filter((p_213238_0_) -> {
            return !p_213238_0_.isEmpty();
         });
      }, (p_213258_0_) -> {
         return p_213258_0_.getRecipes().stream().map((p_213244_0_) -> {
            return Registry.field_212630_s.getKey(p_213244_0_.getRecipeOutput().getItem());
         });
      });
      this.searchTreeManager.func_215357_a(SearchTreeManager.field_215359_a, searchtree);
      this.searchTreeManager.func_215357_a(SearchTreeManager.field_215360_b, searchtreereloadable);
      this.searchTreeManager.func_215357_a(SearchTreeManager.RECIPES, searchtree1);
   }

   private void disableVSyncAfterGlError(int error, long description) {
      this.gameSettings.enableVsync = false;
      this.gameSettings.saveOptions();
   }

   private static boolean isJvm64bit() {
      String[] astring = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};

      for(String s : astring) {
         String s1 = System.getProperty(s);
         if (s1 != null && s1.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public Framebuffer getFramebuffer() {
      return this.framebuffer;
   }

   /**
    * Gets the version that Minecraft was launched under (the name of a version JSON). Specified via the <code>--
    * version</code> flag.
    */
   public String getVersion() {
      return this.launchedVersion;
   }

   /**
    * Gets the type of version that Minecraft was launched under (as specified in the version JSON). Specified via the
    * <code>--versionType</code> flag.
    */
   public String getVersionType() {
      return this.versionType;
   }

   private void startTimerHackThread() {
      Thread thread = new Thread("Timer hack thread") {
         public void run() {
            while(Minecraft.this.running) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var2) {
                  ;
               }
            }

         }
      };
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
      thread.start();
   }

   public void crashed(CrashReport crash) {
      this.hasCrashed = true;
      this.crashReporter = crash;
   }

   /**
    * Wrapper around displayCrashReportInternal
    */
   public void displayCrashReport(CrashReport crashReportIn) {
      File file1 = new File(getInstance().gameDir, "crash-reports");
      File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
      Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport());
      if (crashReportIn.getFile() != null) {
         Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
         net.minecraftforge.fml.server.ServerLifecycleHooks.handleExit(-1);
      } else if (crashReportIn.saveToFile(file2)) {
         Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
         System.exit(-1);
      } else {
         Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }

   }

   public boolean getForceUnicodeFont() {
      return this.gameSettings.forceUnicodeFont;
   }

   @Deprecated // Forge: Use selective refreshResources method in FMLClientHandler
   public CompletableFuture<Void> func_213237_g() {
      if (this.field_213276_aV != null) {
         return this.field_213276_aV;
      } else {
         CompletableFuture<Void> completablefuture = new CompletableFuture<>();
         if (this.field_213279_p instanceof ResourceLoadProgressGui) {
            this.field_213276_aV = completablefuture;
            return completablefuture;
         } else {
            this.resourcePackRepository.reloadPacksFromFinders();
            List<IResourcePack> list = this.resourcePackRepository.getEnabledPacks().stream().map(ResourcePackInfo::getResourcePack).collect(Collectors.toList());
            this.func_213268_a(new ResourceLoadProgressGui(this, this.resourceManager.func_219537_a(Util.func_215072_e(), this, field_213278_d, list), () -> {
               this.languageManager.parseLanguageMetadata(list);
               if (this.worldRenderer != null) {
                  this.worldRenderer.loadRenderers();
               }

               completablefuture.complete((Void)null);
            }, true));
            return completablefuture;
         }
      }
   }

   private void func_213256_aB() {
      boolean flag = false;
      BlockModelShapes blockmodelshapes = this.getBlockRendererDispatcher().getBlockModelShapes();
      IBakedModel ibakedmodel = blockmodelshapes.getModelManager().getMissingModel();

      for(Block block : Registry.field_212618_g) {
         for(BlockState blockstate : block.getStateContainer().getValidStates()) {
            if (blockstate.getRenderType() == BlockRenderType.MODEL) {
               IBakedModel ibakedmodel1 = blockmodelshapes.getModel(blockstate);
               if (ibakedmodel1 == ibakedmodel) {
                  LOGGER.debug("Missing model for: {}", (Object)blockstate);
                  flag = true;
               }
            }
         }
      }

      TextureAtlasSprite textureatlassprite1 = ibakedmodel.getParticleTexture();

      for(Block block1 : Registry.field_212618_g) {
         for(BlockState blockstate1 : block1.getStateContainer().getValidStates()) {
            TextureAtlasSprite textureatlassprite = blockmodelshapes.getTexture(blockstate1);
            if (!blockstate1.isAir() && textureatlassprite == textureatlassprite1) {
               LOGGER.debug("Missing particle icon for: {}", (Object)blockstate1);
               flag = true;
            }
         }
      }

      NonNullList<ItemStack> nonnulllist = NonNullList.create();

      for(Item item : Registry.field_212630_s) {
         nonnulllist.clear();
         item.fillItemGroup(ItemGroup.SEARCH, nonnulllist);

         for(ItemStack itemstack : nonnulllist) {
            String s = itemstack.getTranslationKey();
            String s1 = (new TranslationTextComponent(s)).getString();
            if (s1.toLowerCase(Locale.ROOT).equals(item.getTranslationKey())) {
               LOGGER.debug("Missing translation for: {} {} {}", itemstack, s, itemstack.getItem());
            }
         }
      }

      flag = flag | ScreenManager.func_216910_a();
      if (flag) {
         throw new IllegalStateException("Your game data is foobar, fix the errors above!");
      }
   }

   /**
    * Returns the save loader that is currently being used
    */
   public SaveFormat getSaveLoader() {
      return this.field_71469_aa;
   }

   /**
    * Sets the argument GuiScreen as the main (topmost visible) screen.
    *  
    * <p><strong>WARNING</strong>: This method is not thread-safe. Opening GUIs from a thread other than the main thread
    * may cause many different issues, including the GUI being rendered before it has initialized (leading to unusual
    * crashes). If on a thread other than the main thread, use {@link #addScheduledTask}:
    *  
    * <pre>
    * minecraft.addScheduledTask(() -> minecraft.displayGuiScreen(gui));
    * </pre>
    */
   public void displayGuiScreen(@Nullable Screen guiScreenIn) {
      if (guiScreenIn == null && this.world == null) {
         guiScreenIn = new MainMenuScreen();
      } else if (guiScreenIn == null && this.player.getHealth() <= 0.0F) {
         guiScreenIn = new DeathScreen((ITextComponent)null, this.world.getWorldInfo().isHardcore());
      }

      Screen old = this.field_71462_r;
      net.minecraftforge.client.event.GuiOpenEvent event = new net.minecraftforge.client.event.GuiOpenEvent(guiScreenIn);
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;

      guiScreenIn = event.getGui();
      if (old != null && guiScreenIn != old)
         old.removed();

      if (guiScreenIn instanceof MainMenuScreen || guiScreenIn instanceof MultiplayerScreen) {
         this.gameSettings.showDebugInfo = false;
         this.field_71456_v.getChatGUI().clearChatMessages(true);
      }

      this.field_71462_r = guiScreenIn;
      if (guiScreenIn != null) {
         this.mouseHelper.ungrabMouse();
         KeyBinding.unPressAllKeys();
         guiScreenIn.init(this, this.mainWindow.getScaledWidth(), this.mainWindow.getScaledHeight());
         this.skipRenderWorld = false;
         NarratorChatListener.INSTANCE.func_216864_a(guiScreenIn.getNarrationMessage());
      } else {
         this.soundHandler.resume();
         this.mouseHelper.grabMouse();
      }

   }

   public void func_213268_a(@Nullable LoadingGui p_213268_1_) {
      this.field_213279_p = p_213268_1_;
   }

   /**
    * Shuts down the minecraft applet by stopping the resource downloads, and clearing up GL stuff; called when the
    * application (or web page) is exited.
    */
   public void shutdownMinecraftApplet() {
      try {
         LOGGER.info("Stopping!");
         NarratorChatListener.INSTANCE.func_216867_c();

         try {
            if (this.world != null) {
               this.world.sendQuittingDisconnectingPacket();
            }

            this.func_213254_o();
         } catch (Throwable var5) {
            ;
         }

         if (this.field_71462_r != null) {
            this.field_71462_r.removed();
         }

         this.close();
      } finally {
         Util.nanoTimeSupplier = System::nanoTime;
         if (!this.hasCrashed) {
            System.exit(0);
         }

      }

   }

   public void close() {
      try {
         this.field_147128_au.clear();
         this.fontRenderer.close();
         this.fontResourceMananger.close();
         this.gameRenderer.close();
         this.worldRenderer.close();
         this.soundHandler.unloadSounds();
         this.resourcePackRepository.close();
         this.particles.func_215232_a();
         this.field_213273_aM.close();
         this.field_213272_aL.close();
         Util.func_215082_f();
      } finally {
         this.virtualScreen.close();
         this.mainWindow.close();
      }

   }

   private void runGameLoop(boolean renderWorldIn) {
      this.mainWindow.setRenderPhase("Pre render");
      long i = Util.nanoTime();
      this.field_71424_I.func_219894_a();
      if (GLX.shouldClose(this.mainWindow)) {
         this.shutdown();
      }

      if (this.field_213276_aV != null && !(this.field_213279_p instanceof ResourceLoadProgressGui)) {
         CompletableFuture<Void> completablefuture = this.field_213276_aV;
         this.field_213276_aV = null;
         this.func_213237_g().thenRun(() -> {
            completablefuture.complete((Void)null);
         });
      }

      Runnable runnable;
      while((runnable = this.field_213275_aU.poll()) != null) {
         runnable.run();
      }

      if (renderWorldIn) {
         this.timer.updateTimer(Util.milliTime());
         this.field_71424_I.startSection("scheduledExecutables");
         this.drainTasks();
         this.field_71424_I.endSection();
      }

      long j = Util.nanoTime();
      this.field_71424_I.startSection("tick");
      if (renderWorldIn) {
         for(int k = 0; k < Math.min(10, this.timer.elapsedTicks); ++k) {
            this.runTick();
         }
      }

      this.mouseHelper.updatePlayerLook();
      this.mainWindow.setRenderPhase("Render");
      GLX.pollEvents();
      long i1 = Util.nanoTime() - j;
      this.field_71424_I.endStartSection("sound");
      this.soundHandler.func_215289_a(this.gameRenderer.func_215316_n());
      this.field_71424_I.endSection();
      this.field_71424_I.startSection("render");
      GlStateManager.pushMatrix();
      GlStateManager.clear(16640, IS_RUNNING_ON_MAC);
      this.framebuffer.bindFramebuffer(true);
      this.field_71424_I.startSection("display");
      GlStateManager.enableTexture();
      this.field_71424_I.endSection();
      if (!this.skipRenderWorld) {
         net.minecraftforge.fml.hooks.BasicEventHooks.onRenderTickStart(this.timer.renderPartialTicks);
         this.field_71424_I.endStartSection("gameRenderer");
         this.gameRenderer.updateCameraAndRender(this.isGamePaused ? this.renderPartialTicksPaused : this.timer.renderPartialTicks, i, renderWorldIn);
         this.field_71424_I.endStartSection("toasts");
         this.field_193034_aS.render();
         this.field_71424_I.endSection();
         net.minecraftforge.fml.hooks.BasicEventHooks.onRenderTickEnd(this.timer.renderPartialTicks);
      }

      this.field_71424_I.func_219897_b();
      if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
         this.field_71424_I.func_219899_d().func_219939_d();
         this.drawProfiler();
      } else {
         this.field_71424_I.func_219899_d().func_219938_b();
      }

      this.framebuffer.unbindFramebuffer();
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      this.framebuffer.framebufferRender(this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight());
      GlStateManager.popMatrix();
      this.field_71424_I.func_219894_a();
      this.func_213227_b(true);
      Thread.yield();
      this.mainWindow.setRenderPhase("Post render");
      ++this.fpsCounter;
      boolean flag = this.isSingleplayer() && (this.field_71462_r != null && this.field_71462_r.isPauseScreen() || this.field_213279_p != null && this.field_213279_p.func_212969_a()) && !this.integratedServer.getPublic();
      if (this.isGamePaused != flag) {
         if (this.isGamePaused) {
            this.renderPartialTicksPaused = this.timer.renderPartialTicks;
         } else {
            this.timer.renderPartialTicks = this.renderPartialTicksPaused;
         }

         this.isGamePaused = flag;
      }

      long l = Util.nanoTime();
      this.frameTimer.addFrame(l - this.startNanoTime);
      this.startNanoTime = l;

      while(Util.milliTime() >= this.debugUpdateTime + 1000L) {
         debugFPS = this.fpsCounter;
         this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", debugFPS, ChunkRender.renderChunksUpdated, ChunkRender.renderChunksUpdated == 1 ? "" : "s", (double)this.gameSettings.limitFramerate == AbstractOption.field_216701_h.func_216733_c() ? "inf" : this.gameSettings.limitFramerate, this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", this.gameSettings.field_74345_l == CloudOption.OFF ? "" : (this.gameSettings.field_74345_l == CloudOption.FAST ? " fast-clouds" : " fancy-clouds"), GLX.useVbo() ? " vbo" : "");
         ChunkRender.renderChunksUpdated = 0;
         this.debugUpdateTime += 1000L;
         this.fpsCounter = 0;
         this.snooper.addMemoryStatsToSnooper();
         if (!this.snooper.isSnooperRunning()) {
            this.snooper.start();
         }
      }

      this.field_71424_I.func_219897_b();
   }

   public void func_213227_b(boolean p_213227_1_) {
      this.field_71424_I.startSection("display_update");
      this.mainWindow.update(this.gameSettings.fullScreen);
      this.field_71424_I.endSection();
      if (p_213227_1_ && this.func_213266_aD()) {
         this.field_71424_I.startSection("fpslimit_wait");
         this.mainWindow.func_216524_c();
         this.field_71424_I.endSection();
      }

   }

   public void func_213226_a() {
      int i = this.mainWindow.func_216521_a(this.gameSettings.guiScale, this.getForceUnicodeFont());
      this.mainWindow.func_216525_a((double)i);
      if (this.field_71462_r != null) {
         this.field_71462_r.resize(this, this.mainWindow.getScaledWidth(), this.mainWindow.getScaledHeight());
      }

      Framebuffer framebuffer = this.getFramebuffer();
      if (framebuffer != null) {
         framebuffer.func_216491_a(this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight(), IS_RUNNING_ON_MAC);
      }

      if (this.gameRenderer != null) {
         this.gameRenderer.updateShaderGroupSize(this.mainWindow.getFramebufferWidth(), this.mainWindow.getFramebufferHeight());
      }

      if (this.mouseHelper != null) {
         this.mouseHelper.setIgnoreFirstMove();
      }

   }

   private int func_213243_aC() {
      return this.world != null || this.field_71462_r == null && this.field_213279_p == null ? this.mainWindow.getLimitFramerate() : 60;
   }

   private boolean func_213266_aD() {
      return (double)this.func_213243_aC() < AbstractOption.field_216701_h.func_216733_c();
   }

   /**
    * Attempts to free as much memory as possible, including leaving the world and running the garbage collector.
    */
   public void freeMemory() {
      try {
         memoryReserve = new byte[0];
         this.worldRenderer.deleteAllDisplayLists();
      } catch (Throwable var3) {
         ;
      }

      try {
         System.gc();
         if (this.isSingleplayer()) {
            this.integratedServer.initiateShutdown(true);
         }

         this.func_213231_b(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
      } catch (Throwable var2) {
         ;
      }

      System.gc();
   }

   /**
    * Update debugProfilerName in response to number keys in debug screen
    */
   void updateDebugProfilerName(int keyCount) {
      IProfileResult iprofileresult = this.field_71424_I.func_219899_d().func_219937_c();
      List<DataPoint> list = iprofileresult.func_219917_a(this.debugProfilerName);
      if (!list.isEmpty()) {
         DataPoint datapoint = list.remove(0);
         if (keyCount == 0) {
            if (!datapoint.field_219945_c.isEmpty()) {
               int i = this.debugProfilerName.lastIndexOf(46);
               if (i >= 0) {
                  this.debugProfilerName = this.debugProfilerName.substring(0, i);
               }
            }
         } else {
            --keyCount;
            if (keyCount < list.size() && !"unspecified".equals((list.get(keyCount)).field_219945_c)) {
               if (!this.debugProfilerName.isEmpty()) {
                  this.debugProfilerName = this.debugProfilerName + ".";
               }

               this.debugProfilerName = this.debugProfilerName + (list.get(keyCount)).field_219945_c;
            }
         }

      }
   }

   private void drawProfiler() {
      if (this.field_71424_I.func_219899_d().func_219936_a()) {
         IProfileResult iprofileresult = this.field_71424_I.func_219899_d().func_219937_c();
         List<DataPoint> list = iprofileresult.func_219917_a(this.debugProfilerName);
         DataPoint datapoint = list.remove(0);
         GlStateManager.clear(256, IS_RUNNING_ON_MAC);
         GlStateManager.matrixMode(5889);
         GlStateManager.enableColorMaterial();
         GlStateManager.loadIdentity();
         GlStateManager.ortho(0.0D, (double)this.mainWindow.getFramebufferWidth(), (double)this.mainWindow.getFramebufferHeight(), 0.0D, 1000.0D, 3000.0D);
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         GlStateManager.translatef(0.0F, 0.0F, -2000.0F);
         GlStateManager.lineWidth(1.0F);
         GlStateManager.disableTexture();
         Tessellator tessellator = Tessellator.getInstance();
         BufferBuilder bufferbuilder = tessellator.getBuffer();
         int i = 160;
         int j = this.mainWindow.getFramebufferWidth() - 160 - 10;
         int k = this.mainWindow.getFramebufferHeight() - 320;
         GlStateManager.enableBlend();
         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
         bufferbuilder.pos((double)((float)j - 176.0F), (double)((float)k - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
         bufferbuilder.pos((double)((float)j - 176.0F), (double)(k + 320), 0.0D).color(200, 0, 0, 0).endVertex();
         bufferbuilder.pos((double)((float)j + 176.0F), (double)(k + 320), 0.0D).color(200, 0, 0, 0).endVertex();
         bufferbuilder.pos((double)((float)j + 176.0F), (double)((float)k - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
         tessellator.draw();
         GlStateManager.disableBlend();
         double d0 = 0.0D;

         for(int l = 0; l < list.size(); ++l) {
            DataPoint datapoint1 = list.get(l);
            int i1 = MathHelper.floor(datapoint1.field_219943_a / 4.0D) + 1;
            bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
            int j1 = datapoint1.func_219942_a();
            int k1 = j1 >> 16 & 255;
            int l1 = j1 >> 8 & 255;
            int i2 = j1 & 255;
            bufferbuilder.pos((double)j, (double)k, 0.0D).color(k1, l1, i2, 255).endVertex();

            for(int j2 = i1; j2 >= 0; --j2) {
               float f = (float)((d0 + datapoint1.field_219943_a * (double)j2 / (double)i1) * (double)((float)Math.PI * 2F) / 100.0D);
               float f1 = MathHelper.sin(f) * 160.0F;
               float f2 = MathHelper.cos(f) * 160.0F * 0.5F;
               bufferbuilder.pos((double)((float)j + f1), (double)((float)k - f2), 0.0D).color(k1, l1, i2, 255).endVertex();
            }

            tessellator.draw();
            bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);

            for(int i3 = i1; i3 >= 0; --i3) {
               float f3 = (float)((d0 + datapoint1.field_219943_a * (double)i3 / (double)i1) * (double)((float)Math.PI * 2F) / 100.0D);
               float f4 = MathHelper.sin(f3) * 160.0F;
               float f5 = MathHelper.cos(f3) * 160.0F * 0.5F;
               bufferbuilder.pos((double)((float)j + f4), (double)((float)k - f5), 0.0D).color(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
               bufferbuilder.pos((double)((float)j + f4), (double)((float)k - f5 + 10.0F), 0.0D).color(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
            }

            tessellator.draw();
            d0 += datapoint1.field_219943_a;
         }

         DecimalFormat decimalformat = new DecimalFormat("##0.00");
         decimalformat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
         GlStateManager.enableTexture();
         String s = "";
         if (!"unspecified".equals(datapoint.field_219945_c)) {
            s = s + "[0] ";
         }

         if (datapoint.field_219945_c.isEmpty()) {
            s = s + "ROOT ";
         } else {
            s = s + datapoint.field_219945_c + ' ';
         }

         int l2 = 16777215;
         this.fontRenderer.drawStringWithShadow(s, (float)(j - 160), (float)(k - 80 - 16), 16777215);
         s = decimalformat.format(datapoint.field_219944_b) + "%";
         this.fontRenderer.drawStringWithShadow(s, (float)(j + 160 - this.fontRenderer.getStringWidth(s)), (float)(k - 80 - 16), 16777215);

         for(int k2 = 0; k2 < list.size(); ++k2) {
            DataPoint datapoint2 = list.get(k2);
            StringBuilder stringbuilder = new StringBuilder();
            if ("unspecified".equals(datapoint2.field_219945_c)) {
               stringbuilder.append("[?] ");
            } else {
               stringbuilder.append("[").append(k2 + 1).append("] ");
            }

            String s1 = stringbuilder.append(datapoint2.field_219945_c).toString();
            this.fontRenderer.drawStringWithShadow(s1, (float)(j - 160), (float)(k + 80 + k2 * 8 + 20), datapoint2.func_219942_a());
            s1 = decimalformat.format(datapoint2.field_219943_a) + "%";
            this.fontRenderer.drawStringWithShadow(s1, (float)(j + 160 - 50 - this.fontRenderer.getStringWidth(s1)), (float)(k + 80 + k2 * 8 + 20), datapoint2.func_219942_a());
            s1 = decimalformat.format(datapoint2.field_219944_b) + "%";
            this.fontRenderer.drawStringWithShadow(s1, (float)(j + 160 - this.fontRenderer.getStringWidth(s1)), (float)(k + 80 + k2 * 8 + 20), datapoint2.func_219942_a());
         }

      }
   }

   /**
    * Called when the window is closing. Sets 'running' to false which allows the game loop to exit cleanly.
    */
   public void shutdown() {
      this.running = false;
   }

   /**
    * Displays the ingame menu
    */
   public void displayInGameMenu(boolean p_71385_1_) {
      if (this.field_71462_r == null) {
         boolean flag = this.isSingleplayer() && !this.integratedServer.getPublic();
         if (flag) {
            this.displayGuiScreen(new IngameMenuScreen(!p_71385_1_));
            this.soundHandler.pause();
         } else {
            this.displayGuiScreen(new IngameMenuScreen(true));
         }

      }
   }

   private void sendClickBlockToController(boolean leftClick) {
      if (!leftClick) {
         this.leftClickCounter = 0;
      }

      if (this.leftClickCounter <= 0 && !this.player.isHandActive()) {
         if (leftClick && this.objectMouseOver != null && this.objectMouseOver.getType() == RayTraceResult.Type.BLOCK) {
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)this.objectMouseOver;
            BlockPos blockpos = blockraytraceresult.getPos();
            if (!this.world.isAirBlock(blockpos)) {
               Direction direction = blockraytraceresult.getFace();
               if (this.field_71442_b.onPlayerDamageBlock(blockpos, direction)) {
                  this.particles.addBlockHitEffects(blockpos, blockraytraceresult);
                  this.player.swingArm(Hand.MAIN_HAND);
               }
            }

         } else {
            this.field_71442_b.resetBlockRemoving();
         }
      }
   }

   private void clickMouse() {
      if (this.leftClickCounter <= 0) {
         if (this.objectMouseOver == null) {
            LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.field_71442_b.isNotCreative()) {
               this.leftClickCounter = 10;
            }

         } else if (!this.player.isRowingBoat()) {
            switch(this.objectMouseOver.getType()) {
            case ENTITY:
               this.field_71442_b.attackEntity(this.player, ((EntityRayTraceResult)this.objectMouseOver).getEntity());
               break;
            case BLOCK:
               BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)this.objectMouseOver;
               BlockPos blockpos = blockraytraceresult.getPos();
               if (!this.world.getBlockState(blockpos).isAir(world, blockpos)) {
                  this.field_71442_b.clickBlock(blockpos, blockraytraceresult.getFace());
                  break;
               }
            case MISS:
               if (this.field_71442_b.isNotCreative()) {
                  this.leftClickCounter = 10;
               }

               this.player.resetCooldown();
               net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(this.player);
            }

            this.player.swingArm(Hand.MAIN_HAND);
         }
      }
   }

   /**
    * Called when user clicked he's mouse right button (place)
    */
   private void rightClickMouse() {
      if (!this.field_71442_b.getIsHittingBlock()) {
         this.rightClickDelayTimer = 4;
         if (!this.player.isRowingBoat()) {
            if (this.objectMouseOver == null) {
               LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!");
            }

            for(Hand hand : Hand.values()) {
               ItemStack itemstack = this.player.getHeldItem(hand);
               if (this.objectMouseOver != null) {
                  switch(this.objectMouseOver.getType()) {
                  case ENTITY:
                     EntityRayTraceResult entityraytraceresult = (EntityRayTraceResult)this.objectMouseOver;
                     Entity entity = entityraytraceresult.getEntity();
                     if (this.field_71442_b.interactWithEntity(this.player, entity, entityraytraceresult, hand) == ActionResultType.SUCCESS) {
                        return;
                     }

                     if (this.field_71442_b.interactWithEntity(this.player, entity, hand) == ActionResultType.SUCCESS) {
                        return;
                     }
                     break;
                  case BLOCK:
                     BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)this.objectMouseOver;
                     int i = itemstack.getCount();
                     ActionResultType actionresulttype = this.field_71442_b.func_217292_a(this.player, this.world, hand, blockraytraceresult);
                     if (actionresulttype == ActionResultType.SUCCESS) {
                        this.player.swingArm(hand);
                        if (!itemstack.isEmpty() && (itemstack.getCount() != i || this.field_71442_b.isInCreativeMode())) {
                           this.gameRenderer.itemRenderer.resetEquippedProgress(hand);
                        }

                        return;
                     }

                     if (actionresulttype == ActionResultType.FAIL) {
                        return;
                     }
                  }
               }

               if (itemstack.isEmpty() && (this.objectMouseOver == null || this.objectMouseOver.getType() == RayTraceResult.Type.MISS))
                  net.minecraftforge.common.ForgeHooks.onEmptyClick(this.player, hand);

               if (!itemstack.isEmpty() && this.field_71442_b.processRightClick(this.player, this.world, hand) == ActionResultType.SUCCESS) {
                  this.gameRenderer.itemRenderer.resetEquippedProgress(hand);
                  return;
               }
            }

         }
      }
   }

   /**
    * Return the musicTicker's instance
    */
   public MusicTicker getMusicTicker() {
      return this.musicTicker;
   }

   /**
    * Runs the current tick.
    */
   public void runTick() {
      if (this.rightClickDelayTimer > 0) {
         --this.rightClickDelayTimer;
      }

      net.minecraftforge.fml.hooks.BasicEventHooks.onPreClientTick();

      this.field_71424_I.startSection("gui");
      if (!this.isGamePaused) {
         this.field_71456_v.tick();
      }

      this.field_71424_I.endSection();
      this.gameRenderer.getMouseOver(1.0F);
      this.tutorial.onMouseHover(this.world, this.objectMouseOver);
      this.field_71424_I.startSection("gameMode");
      if (!this.isGamePaused && this.world != null) {
         this.field_71442_b.tick();
      }

      this.field_71424_I.endStartSection("textures");
      if (this.world != null) {
         this.textureManager.tick();
      }

      if (this.field_71462_r == null && this.player != null) {
         if (this.player.getHealth() <= 0.0F && !(this.field_71462_r instanceof DeathScreen)) {
            this.displayGuiScreen((Screen)null);
         } else if (this.player.isPlayerSleeping() && this.world != null) {
            this.displayGuiScreen(new SleepInMultiplayerScreen());
         }
      } else if (this.field_71462_r != null && this.field_71462_r instanceof SleepInMultiplayerScreen && !this.player.isPlayerSleeping()) {
         this.displayGuiScreen((Screen)null);
      }

      if (this.field_71462_r != null) {
         this.leftClickCounter = 10000;
      }

      if (this.field_71462_r != null) {
         Screen.wrapScreenError(() -> {
            this.field_71462_r.tick();
         }, "Ticking screen", this.field_71462_r.getClass().getCanonicalName());
      }

      if (!this.gameSettings.showDebugInfo) {
         this.field_71456_v.func_212910_m();
      }

      if (this.field_213279_p == null && (this.field_71462_r == null || this.field_71462_r.passEvents)) {
         this.field_71424_I.endStartSection("GLFW events");
         GLX.pollEvents();
         this.processKeyBinds();
         if (this.leftClickCounter > 0) {
            --this.leftClickCounter;
         }
      }

      if (this.world != null) {
         this.field_71424_I.endStartSection("gameRenderer");
         if (!this.isGamePaused) {
            this.gameRenderer.tick();
         }

         this.field_71424_I.endStartSection("levelRenderer");
         if (!this.isGamePaused) {
            this.worldRenderer.tick();
         }

         this.field_71424_I.endStartSection("level");
         if (!this.isGamePaused) {
            if (this.world.getLastLightningBolt() > 0) {
               this.world.setLastLightningBolt(this.world.getLastLightningBolt() - 1);
            }

            this.world.func_217419_d();
         }
      } else if (this.gameRenderer.isShaderActive()) {
         this.gameRenderer.stopUseShader();
      }

      if (!this.isGamePaused) {
         this.musicTicker.tick();
      }

      this.soundHandler.func_215290_a(this.isGamePaused);
      if (this.world != null) {
         if (!this.isGamePaused) {
            this.world.setAllowedSpawnTypes(this.world.getDifficulty() != Difficulty.PEACEFUL, true);
            this.tutorial.tick();

            try {
               this.world.tick(() -> {
                  return true;
               });
            } catch (Throwable throwable) {
               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception in world tick");
               if (this.world == null) {
                  CrashReportCategory crashreportcategory = crashreport.makeCategory("Affected level");
                  crashreportcategory.addDetail("Problem", "Level is null!");
               } else {
                  this.world.fillCrashReport(crashreport);
               }

               throw new ReportedException(crashreport);
            }
         }

         this.field_71424_I.endStartSection("animateTick");
         if (!this.isGamePaused && this.world != null) {
            this.world.animateTick(MathHelper.floor(this.player.posX), MathHelper.floor(this.player.posY), MathHelper.floor(this.player.posZ));
         }

         this.field_71424_I.endStartSection("particles");
         if (!this.isGamePaused) {
            this.particles.tick();
         }
      } else if (this.networkManager != null) {
         this.field_71424_I.endStartSection("pendingConnection");
         this.networkManager.tick();
      }

      this.field_71424_I.endStartSection("keyboard");
      this.keyboardListener.tick();
      this.field_71424_I.endSection();

      net.minecraftforge.fml.hooks.BasicEventHooks.onPostClientTick();
   }

   private void processKeyBinds() {
      for(; this.gameSettings.keyBindTogglePerspective.isPressed(); this.worldRenderer.setDisplayListEntitiesDirty()) {
         ++this.gameSettings.thirdPersonView;
         if (this.gameSettings.thirdPersonView > 2) {
            this.gameSettings.thirdPersonView = 0;
         }

         if (this.gameSettings.thirdPersonView == 0) {
            this.gameRenderer.loadEntityShader(this.getRenderViewEntity());
         } else if (this.gameSettings.thirdPersonView == 1) {
            this.gameRenderer.loadEntityShader((Entity)null);
         }
      }

      while(this.gameSettings.keyBindSmoothCamera.isPressed()) {
         this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
      }

      for(int i = 0; i < 9; ++i) {
         boolean flag = this.gameSettings.keyBindSaveToolbar.isKeyDown();
         boolean flag1 = this.gameSettings.keyBindLoadToolbar.isKeyDown();
         if (this.gameSettings.keyBindsHotbar[i].isPressed()) {
            if (this.player.isSpectator()) {
               this.field_71456_v.getSpectatorGui().onHotbarSelected(i);
            } else if (!this.player.isCreative() || this.field_71462_r != null || !flag1 && !flag) {
               this.player.inventory.currentItem = i;
            } else {
               CreativeScreen.handleHotbarSnapshots(this, i, flag1, flag);
            }
         }
      }

      while(this.gameSettings.keyBindInventory.isPressed()) {
         if (this.field_71442_b.isRidingHorse()) {
            this.player.sendHorseInventory();
         } else {
            this.tutorial.openInventory();
            this.displayGuiScreen(new InventoryScreen(this.player));
         }
      }

      while(this.gameSettings.keyBindAdvancements.isPressed()) {
         this.displayGuiScreen(new AdvancementsScreen(this.player.field_71174_a.getAdvancementManager()));
      }

      while(this.gameSettings.keyBindSwapHands.isPressed()) {
         if (!this.player.isSpectator()) {
            this.getConnection().sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.SWAP_HELD_ITEMS, BlockPos.ZERO, Direction.DOWN));
         }
      }

      while(this.gameSettings.keyBindDrop.isPressed()) {
         if (!this.player.isSpectator()) {
            this.player.dropItem(Screen.hasControlDown());
         }
      }
      if (world != null) net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Unload(world));

      boolean flag2 = this.gameSettings.field_74343_n != ChatVisibility.HIDDEN;
      if (flag2) {
         while(this.gameSettings.keyBindChat.isPressed()) {
            this.displayGuiScreen(new ChatScreen(""));
         }

         if (this.field_71462_r == null && this.field_213279_p == null && this.gameSettings.keyBindCommand.isPressed()) {
            this.displayGuiScreen(new ChatScreen("/"));
         }
      }

      if (this.player.isHandActive()) {
         if (!this.gameSettings.keyBindUseItem.isKeyDown()) {
            this.field_71442_b.onStoppedUsingItem(this.player);
         }

         while(this.gameSettings.keyBindAttack.isPressed()) {
            ;
         }

         while(this.gameSettings.keyBindUseItem.isPressed()) {
            ;
         }

         while(this.gameSettings.keyBindPickBlock.isPressed()) {
            ;
         }
      } else {
         while(this.gameSettings.keyBindAttack.isPressed()) {
            this.clickMouse();
         }

         while(this.gameSettings.keyBindUseItem.isPressed()) {
            this.rightClickMouse();
         }

         while(this.gameSettings.keyBindPickBlock.isPressed()) {
            this.middleClickMouse();
         }
      }

      if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.player.isHandActive()) {
         this.rightClickMouse();
      }

      this.sendClickBlockToController(this.field_71462_r == null && this.gameSettings.keyBindAttack.isKeyDown() && this.mouseHelper.isMouseGrabbed());
   }

   /**
    * Arguments: World foldername,  World ingame name, WorldSettings
    */
   public void launchIntegratedServer(String folderName, String worldName, @Nullable WorldSettings worldSettingsIn) {
      this.func_213254_o();
      SaveHandler savehandler = this.field_71469_aa.getSaveLoader(folderName, (MinecraftServer)null);
      WorldInfo worldinfo = savehandler.loadWorldInfo();
      if (worldinfo == null && worldSettingsIn != null) {
         worldinfo = new WorldInfo(worldSettingsIn, folderName);
         savehandler.saveWorldInfo(worldinfo);
      }

      if (worldSettingsIn == null) {
         worldSettingsIn = new WorldSettings(worldinfo);
      }

      this.field_213277_ad.set((TrackingChunkStatusListener)null);

      try {
         YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(this.proxy, UUID.randomUUID().toString());
         MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
         GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
         PlayerProfileCache playerprofilecache = new PlayerProfileCache(gameprofilerepository, new File(this.gameDir, MinecraftServer.USER_CACHE_FILE.getName()));
         SkullTileEntity.setProfileCache(playerprofilecache);
         SkullTileEntity.setSessionService(minecraftsessionservice);
         PlayerProfileCache.setOnlineMode(false);
         this.integratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn, yggdrasilauthenticationservice, minecraftsessionservice, gameprofilerepository, playerprofilecache, (p_213246_1_) -> {
            TrackingChunkStatusListener trackingchunkstatuslistener = new TrackingChunkStatusListener(p_213246_1_ + 0);
            trackingchunkstatuslistener.func_219521_a();
            this.field_213277_ad.set(trackingchunkstatuslistener);
            return new ChainedChunkStatusListener(trackingchunkstatuslistener, this.field_213275_aU::add);
         });
         this.integratedServer.startServerThread();
         this.integratedServerIsRunning = true;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Starting integrated server");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Starting integrated server");
         crashreportcategory.addDetail("Level ID", folderName);
         crashreportcategory.addDetail("Level Name", worldName);
         throw new ReportedException(crashreport);
      }

      while(this.field_213277_ad.get() == null) {
         Thread.yield();
      }

      WorldLoadProgressScreen worldloadprogressscreen = new WorldLoadProgressScreen(this.field_213277_ad.get());
      this.displayGuiScreen(worldloadprogressscreen);

      while(!this.integratedServer.serverIsInRunLoop()) {
         if (!net.minecraftforge.fml.StartupQuery.check() || this.integratedServer.isServerStopped()) {
            this.displayGuiScreen(null);
            return;
         } else if (this.field_71462_r == null) // if we're showing nothing, put the working screen back again
           this.displayGuiScreen(worldloadprogressscreen);

         worldloadprogressscreen.tick();
         this.runGameLoop(false);

         try {
            Thread.sleep(16L);
         } catch (InterruptedException var10) {
            ;
         }

         if (this.hasCrashed && this.crashReporter != null) {
            this.displayCrashReport(this.crashReporter);
            return;
         }
      }

      SocketAddress socketaddress = this.integratedServer.getNetworkSystem().addLocalEndpoint();
      NetworkManager networkmanager = NetworkManager.provideLocalClient(socketaddress);
      networkmanager.setNetHandler(new ClientLoginNetHandler(networkmanager, this, (Screen)null, (p_213261_0_) -> {
      }));
      networkmanager.sendPacket(new CHandshakePacket(socketaddress.toString(), 0, ProtocolType.LOGIN));
      com.mojang.authlib.GameProfile gameProfile = this.getSession().getProfile();
      if (!this.getSession().hasCachedProperties()) {
         gameProfile = sessionService.fillProfileProperties(gameProfile, true); //Forge: Fill profile properties upon game load. Fixes MC-52974.
         this.getSession().setProperties(gameProfile.getProperties());
      }
      networkmanager.sendPacket(new CLoginStartPacket(gameProfile));
      this.networkManager = networkmanager;
   }

   /**
    * unloads the current world first
    */
   public void loadWorld(ClientWorld worldClientIn) {
      if (world != null) net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Unload(world));
      WorkingScreen workingscreen = new WorkingScreen();
      workingscreen.displaySavingString(new TranslationTextComponent("connect.joining"));
      this.func_213241_c(workingscreen);
      this.world = worldClientIn;
      this.func_213257_b(worldClientIn);
      if (!this.integratedServerIsRunning) {
         AuthenticationService authenticationservice = new YggdrasilAuthenticationService(this.proxy, UUID.randomUUID().toString());
         MinecraftSessionService minecraftsessionservice = authenticationservice.createMinecraftSessionService();
         GameProfileRepository gameprofilerepository = authenticationservice.createProfileRepository();
         PlayerProfileCache playerprofilecache = new PlayerProfileCache(gameprofilerepository, new File(this.gameDir, MinecraftServer.USER_CACHE_FILE.getName()));
         SkullTileEntity.setProfileCache(playerprofilecache);
         SkullTileEntity.setSessionService(minecraftsessionservice);
         PlayerProfileCache.setOnlineMode(false);
      }

   }

   public void func_213254_o() {
      this.func_213231_b(new WorkingScreen());
   }

   public void func_213231_b(Screen p_213231_1_) {
      ClientPlayNetHandler clientplaynethandler = this.getConnection();
      if (clientplaynethandler != null) {
         this.dropTasks();
         clientplaynethandler.cleanup();
      }

      IntegratedServer integratedserver = this.integratedServer;
      this.integratedServer = null;
      this.gameRenderer.resetData();
      this.field_71442_b = null;
      NarratorChatListener.INSTANCE.clear();
      this.func_213241_c(p_213231_1_);
      if (this.world != null) {
         if (integratedserver != null) {
            while(!integratedserver.func_213201_w()) {
               this.runGameLoop(false);
            }
         }

         this.packFinder.clearResourcePack();
         this.field_71456_v.resetPlayersOverlayFooterHeader();
         this.setServerData((ServerData)null);
         this.integratedServerIsRunning = false;
         this.field_213274_aO.func_216815_b();
      }

      this.world = null;
      this.func_213257_b((ClientWorld)null);
      this.player = null;
   }

   private void func_213241_c(Screen p_213241_1_) {
      this.musicTicker.stop();
      this.soundHandler.stop();
      this.renderViewEntity = null;
      this.networkManager = null;
      this.displayGuiScreen(p_213241_1_);
      this.runGameLoop(false);
   }

   private void func_213257_b(@Nullable ClientWorld p_213257_1_) {
      if (this.worldRenderer != null) {
         this.worldRenderer.setWorldAndLoadRenderers(p_213257_1_);
      }

      if (this.particles != null) {
         this.particles.clearEffects(p_213257_1_);
      }

      TileEntityRendererDispatcher.instance.setWorld(p_213257_1_);
      net.minecraftforge.client.MinecraftForgeClient.clearRenderCache();
   }

   /**
    * Gets whether this is a demo or not.
    */
   public final boolean isDemo() {
      return this.isDemo;
   }

   @Nullable
   public ClientPlayNetHandler getConnection() {
      return this.player == null ? null : this.player.field_71174_a;
   }

   public static boolean isGuiEnabled() {
      return instance == null || !instance.gameSettings.hideGUI;
   }

   public static boolean isFancyGraphicsEnabled() {
      return instance != null && instance.gameSettings.fancyGraphics;
   }

   /**
    * Returns if ambient occlusion is enabled
    */
   public static boolean isAmbientOcclusionEnabled() {
      return instance != null && instance.gameSettings.ambientOcclusionStatus != AmbientOcclusionStatus.OFF;
   }

   /**
    * Called when user clicked he's mouse middle button (pick block)
    */
   private void middleClickMouse() {
      if (this.objectMouseOver != null && this.objectMouseOver.getType() != RayTraceResult.Type.MISS) {
         net.minecraftforge.common.ForgeHooks.onPickBlock(this.objectMouseOver, this.player, this.world);
         // We delete this code wholly instead of commenting it out, to make sure we detect changes in it between MC versions
      }
   }

   public ItemStack storeTEInStack(ItemStack stack, TileEntity te) {
      CompoundNBT compoundnbt = te.write(new CompoundNBT());
      if (stack.getItem() instanceof SkullItem && compoundnbt.contains("Owner")) {
         CompoundNBT compoundnbt2 = compoundnbt.getCompound("Owner");
         stack.getOrCreateTag().put("SkullOwner", compoundnbt2);
         return stack;
      } else {
         stack.setTagInfo("BlockEntityTag", compoundnbt);
         CompoundNBT compoundnbt1 = new CompoundNBT();
         ListNBT listnbt = new ListNBT();
         listnbt.add(new StringNBT("\"(+NBT)\""));
         compoundnbt1.put("Lore", listnbt);
         stack.setTagInfo("display", compoundnbt1);
         return stack;
      }
   }

   /**
    * adds core server Info (GL version , Texture pack, isModded, type), and the worldInfo to the crash report
    */
   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
      CrashReportCategory crashreportcategory = theCrash.getCategory();
      crashreportcategory.addDetail("Launched Version", () -> {
         return this.launchedVersion;
      });
      crashreportcategory.addDetail("LWJGL", GLX::getLWJGLVersion);
      crashreportcategory.addDetail("OpenGL", GLX::getOpenGLVersionString);
      crashreportcategory.addDetail("GL Caps", GLX::getCapsString);
      crashreportcategory.addDetail("Using VBOs", () -> {
         return "Yes";
      });
      crashreportcategory.addDetail("Is Modded", () -> {
         String s = ClientBrandRetriever.getClientModName();
         if (!"vanilla".equals(s)) {
            return "Definitely; Client brand changed to '" + s + "'";
         } else {
            return Minecraft.class.getSigners() == null ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.";
         }
      });
      crashreportcategory.addDetail("Type", "Client (map_client.txt)");
      crashreportcategory.addDetail("Resource Packs", () -> {
         StringBuilder stringbuilder = new StringBuilder();

         for(String s : this.gameSettings.resourcePacks) {
            if (stringbuilder.length() > 0) {
               stringbuilder.append(", ");
            }

            stringbuilder.append(s);
            if (this.gameSettings.incompatibleResourcePacks.contains(s)) {
               stringbuilder.append(" (incompatible)");
            }
         }

         return stringbuilder.toString();
      });
      crashreportcategory.addDetail("Current Language", () -> {
         return this.languageManager.getCurrentLanguage().toString();
      });
      crashreportcategory.addDetail("CPU", GLX::getCpuInfo);
      if (this.world != null) {
         this.world.fillCrashReport(theCrash);
      }

      return theCrash;
   }

   /**
    * Return the singleton Minecraft instance for the game
    */
   public static Minecraft getInstance() {
      return instance;
   }

   @Deprecated // Forge: Use selective scheduleResourceRefresh method in FMLClientHandler
   public CompletableFuture<Void> func_213245_w() {
      return this.supplyAsync(this::func_213237_g).thenCompose((p_213240_0_) -> {
         return p_213240_0_;
      });
   }

   public void fillSnooper(Snooper snooper) {
      snooper.addClientStat("fps", debugFPS);
      snooper.addClientStat("vsync_enabled", this.gameSettings.enableVsync);
      int i = GLX.getRefreshRate(this.mainWindow);
      snooper.addClientStat("display_frequency", i);
      snooper.addClientStat("display_type", this.mainWindow.isFullscreen() ? "fullscreen" : "windowed");
      snooper.addClientStat("run_time", (Util.milliTime() - snooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
      snooper.addClientStat("current_action", this.getCurrentAction());
      snooper.addClientStat("language", this.gameSettings.language == null ? "en_us" : this.gameSettings.language);
      String s = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      snooper.addClientStat("endianness", s);
      snooper.addClientStat("subtitles", this.gameSettings.showSubtitles);
      snooper.addClientStat("touch", this.gameSettings.touchscreen ? "touch" : "mouse");
      int j = 0;

      for(ClientResourcePackInfo clientresourcepackinfo : this.resourcePackRepository.getEnabledPacks()) {
         if (!clientresourcepackinfo.isAlwaysEnabled() && !clientresourcepackinfo.isOrderLocked()) {
            snooper.addClientStat("resource_pack[" + j++ + "]", clientresourcepackinfo.getName());
         }
      }

      snooper.addClientStat("resource_packs", j);
      if (this.integratedServer != null && this.integratedServer.getSnooper() != null) {
         snooper.addClientStat("snooper_partner", this.integratedServer.getSnooper().getUniqueID());
      }

   }

   /**
    * Return the current action's name
    */
   private String getCurrentAction() {
      if (this.integratedServer != null) {
         return this.integratedServer.getPublic() ? "hosting_lan" : "singleplayer";
      } else if (this.currentServerData != null) {
         return this.currentServerData.isOnLAN() ? "playing_lan" : "multiplayer";
      } else {
         return "out_of_game";
      }
   }

   /**
    * Used in the usage snooper.
    */
   public static int getGLMaximumTextureSize() {
      if (cachedMaximumTextureSize == -1) {
         for(int i = 16384; i > 0; i >>= 1) {
            GlStateManager.texImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (IntBuffer)null);
            int j = GlStateManager.getTexLevelParameter(32868, 0, 4096);
            if (j != 0) {
               cachedMaximumTextureSize = i;
               return i;
            }
         }
      }

      return cachedMaximumTextureSize;
   }

   /**
    * Set the current ServerData instance.
    */
   public void setServerData(ServerData serverDataIn) {
      this.currentServerData = serverDataIn;
   }

   @Nullable
   public ServerData getCurrentServerData() {
      return this.currentServerData;
   }

   public boolean isIntegratedServerRunning() {
      return this.integratedServerIsRunning;
   }

   /**
    * Returns true if there is only one player playing, and the current server is the integrated one.
    */
   public boolean isSingleplayer() {
      return this.integratedServerIsRunning && this.integratedServer != null;
   }

   /**
    * Returns the currently running integrated server
    */
   @Nullable
   public IntegratedServer getIntegratedServer() {
      return this.integratedServer;
   }

   /**
    * Returns the PlayerUsageSnooper instance.
    */
   public Snooper getSnooper() {
      return this.snooper;
   }

   public Session getSession() {
      return this.session;
   }

   /**
    * Return the player's GameProfile properties
    */
   public PropertyMap getProfileProperties() {
      if (this.profileProperties.isEmpty()) {
         GameProfile gameprofile = this.getSessionService().fillProfileProperties(this.session.getProfile(), false);
         this.profileProperties.putAll(gameprofile.getProperties());
      }

      return this.profileProperties;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public TextureManager getTextureManager() {
      return this.textureManager;
   }

   public IResourceManager getResourceManager() {
      return this.resourceManager;
   }

   public ResourcePackList<ClientResourcePackInfo> getResourcePackList() {
      return this.resourcePackRepository;
   }

   public DownloadingPackFinder getPackFinder() {
      return this.packFinder;
   }

   public File getFileResourcePacks() {
      return this.fileResourcepacks;
   }

   public LanguageManager getLanguageManager() {
      return this.languageManager;
   }

   public AtlasTexture getTextureMap() {
      return this.field_147128_au;
   }

   public boolean isJava64bit() {
      return this.jvm64bit;
   }

   public boolean isGamePaused() {
      return this.isGamePaused;
   }

   public SoundHandler getSoundHandler() {
      return this.soundHandler;
   }

   public MusicTicker.MusicType getAmbientMusicType() {
      MusicTicker.MusicType type = this.world == null || this.world.dimension == null ? null : this.world.dimension.getMusicType();
      if (type != null) return type;
      if (this.field_71462_r instanceof WinGameScreen) {
         return MusicTicker.MusicType.CREDITS;
      } else if (this.player == null) {
         return MusicTicker.MusicType.MENU;
      } else if (this.player.world.dimension instanceof NetherDimension) {
         return MusicTicker.MusicType.NETHER;
      } else if (this.player.world.dimension instanceof EndDimension) {
         return this.field_71456_v.getBossOverlay().shouldPlayEndBossMusic() ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END;
      } else {
         Biome.Category biome$category = this.player.world.getBiome(new BlockPos(this.player)).getCategory();
         if (!this.musicTicker.isPlaying(MusicTicker.MusicType.UNDER_WATER) && (!this.player.canSwim() || this.musicTicker.isPlaying(MusicTicker.MusicType.GAME) || biome$category != Biome.Category.OCEAN && biome$category != Biome.Category.RIVER)) {
            return this.player.playerAbilities.isCreativeMode && this.player.playerAbilities.allowFlying ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME;
         } else {
            return MusicTicker.MusicType.UNDER_WATER;
         }
      }
   }

   public MinecraftSessionService getSessionService() {
      return this.sessionService;
   }

   public SkinManager getSkinManager() {
      return this.skinManager;
   }

   @Nullable
   public Entity getRenderViewEntity() {
      return this.renderViewEntity;
   }

   public void setRenderViewEntity(Entity viewingEntity) {
      this.renderViewEntity = viewingEntity;
      this.gameRenderer.loadEntityShader(viewingEntity);
   }

   protected Thread getExecutionThread() {
      return this.thread;
   }

   protected Runnable wrapTask(Runnable p_212875_1_) {
      return p_212875_1_;
   }

   protected boolean canRun(Runnable p_212874_1_) {
      return true;
   }

   public BlockRendererDispatcher getBlockRendererDispatcher() {
      return this.blockRenderDispatcher;
   }

   public EntityRendererManager getRenderManager() {
      return this.field_175616_W;
   }

   public ItemRenderer getItemRenderer() {
      return this.itemRenderer;
   }

   public FirstPersonRenderer getFirstPersonRenderer() {
      return this.firstPersonRenderer;
   }

   public <T> IMutableSearchTree<T> func_213253_a(SearchTreeManager.Key<T> p_213253_1_) {
      return this.searchTreeManager.func_215358_a(p_213253_1_);
   }

   public static int getDebugFPS() {
      return debugFPS;
   }

   /**
    * Return the FrameTimer's instance
    */
   public FrameTimer getFrameTimer() {
      return this.frameTimer;
   }

   /**
    * Return true if the player is connected to a realms server
    */
   public boolean isConnectedToRealms() {
      return this.connectedToRealms;
   }

   /**
    * Set if the player is connected to a realms server
    */
   public void setConnectedToRealms(boolean isConnected) {
      this.connectedToRealms = isConnected;
   }

   public DataFixer getDataFixer() {
      return this.dataFixer;
   }

   public float getRenderPartialTicks() {
      return this.timer.renderPartialTicks;
   }

   public float getTickLength() {
      return this.timer.elapsedPartialTicks;
   }

   public BlockColors getBlockColors() {
      return this.blockColors;
   }

   /**
    * Whether to use reduced debug info
    */
   public boolean isReducedDebug() {
      return this.player != null && this.player.hasReducedDebug() || this.gameSettings.reducedDebugInfo;
   }

   public ToastGui getToastGui() {
      return this.field_193034_aS;
   }

   public Tutorial getTutorial() {
      return this.tutorial;
   }

   public boolean isGameFocused() {
      return this.isWindowFocused;
   }

   public CreativeSettings getCreativeSettings() {
      return this.creativeSettings;
   }

   public ModelManager getModelManager() {
      return this.modelManager;
   }

   public FontResourceManager getFontResourceManager() {
      return this.fontResourceMananger;
   }

   public PaintingSpriteUploader func_213263_ao() {
      return this.field_213272_aL;
   }

   public PotionSpriteUploader func_213248_ap() {
      return this.field_213273_aM;
   }

   public void func_213228_a(boolean p_213228_1_) {
      this.isWindowFocused = p_213228_1_;
   }

   public IProfiler getProfiler() {
      return this.field_71424_I;
   }

   public MinecraftGame func_213229_ar() {
      return this.field_213274_aO;
   }

   public Splashes func_213269_at() {
      return this.field_213271_aF;
   }

   @Nullable
   public LoadingGui func_213250_au() {
      return this.field_213279_p;
   }

   public ItemColors getItemColors() {
      return this.itemColors;
   }

   public SearchTreeManager getSearchTreeManager() {
      return this.searchTreeManager;
   }
}