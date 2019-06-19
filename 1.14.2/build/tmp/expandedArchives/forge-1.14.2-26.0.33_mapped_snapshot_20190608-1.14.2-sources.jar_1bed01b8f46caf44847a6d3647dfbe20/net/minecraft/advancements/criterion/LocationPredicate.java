package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;

public class LocationPredicate {
   public static final LocationPredicate ANY = new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, (Biome)null, (Structure<?>)null, (DimensionType)null);
   private final MinMaxBounds.FloatBound x;
   private final MinMaxBounds.FloatBound y;
   private final MinMaxBounds.FloatBound z;
   @Nullable
   private final Biome biome;
   @Nullable
   private final Structure<?> field_193460_f;
   @Nullable
   private final DimensionType dimension;

   public LocationPredicate(MinMaxBounds.FloatBound p_i50802_1_, MinMaxBounds.FloatBound p_i50802_2_, MinMaxBounds.FloatBound p_i50802_3_, @Nullable Biome p_i50802_4_, @Nullable Structure<?> p_i50802_5_, @Nullable DimensionType p_i50802_6_) {
      this.x = p_i50802_1_;
      this.y = p_i50802_2_;
      this.z = p_i50802_3_;
      this.biome = p_i50802_4_;
      this.field_193460_f = p_i50802_5_;
      this.dimension = p_i50802_6_;
   }

   public static LocationPredicate forBiome(Biome p_204010_0_) {
      return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, p_204010_0_, (Structure<?>)null, (DimensionType)null);
   }

   public static LocationPredicate forDimension(DimensionType p_204008_0_) {
      return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, (Biome)null, (Structure<?>)null, p_204008_0_);
   }

   public static LocationPredicate func_218020_a(Structure<?> p_218020_0_) {
      return new LocationPredicate(MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, MinMaxBounds.FloatBound.UNBOUNDED, (Biome)null, p_218020_0_, (DimensionType)null);
   }

   public boolean test(ServerWorld world, double x, double y, double z) {
      return this.test(world, (float)x, (float)y, (float)z);
   }

   public boolean test(ServerWorld world, float x, float y, float z) {
      if (!this.x.test(x)) {
         return false;
      } else if (!this.y.test(y)) {
         return false;
      } else if (!this.z.test(z)) {
         return false;
      } else if (this.dimension != null && this.dimension != world.dimension.getType()) {
         return false;
      } else {
         BlockPos blockpos = new BlockPos((double)x, (double)y, (double)z);
         if (this.biome != null && this.biome != world.getBiome(blockpos)) {
            return false;
         } else {
            return this.field_193460_f == null || this.field_193460_f.isPositionInsideStructure(world, blockpos);
         }
      }
   }

   public JsonElement serialize() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (!this.x.isUnbounded() || !this.y.isUnbounded() || !this.z.isUnbounded()) {
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.add("x", this.x.serialize());
            jsonobject1.add("y", this.y.serialize());
            jsonobject1.add("z", this.z.serialize());
            jsonobject.add("position", jsonobject1);
         }

         if (this.dimension != null) {
            jsonobject.addProperty("dimension", DimensionType.getKey(this.dimension).toString());
         }

         if (this.field_193460_f != null) {
            jsonobject.addProperty("feature", Feature.field_202300_at.inverse().get(this.field_193460_f));
         }

         if (this.biome != null) {
            jsonobject.addProperty("biome", Registry.field_212624_m.getKey(this.biome).toString());
         }

         return jsonobject;
      }
   }

   public static LocationPredicate deserialize(@Nullable JsonElement element) {
      if (element != null && !element.isJsonNull()) {
         JsonObject jsonobject = JSONUtils.getJsonObject(element, "location");
         JsonObject jsonobject1 = JSONUtils.getJsonObject(jsonobject, "position", new JsonObject());
         MinMaxBounds.FloatBound minmaxbounds$floatbound = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("x"));
         MinMaxBounds.FloatBound minmaxbounds$floatbound1 = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("y"));
         MinMaxBounds.FloatBound minmaxbounds$floatbound2 = MinMaxBounds.FloatBound.fromJson(jsonobject1.get("z"));
         DimensionType dimensiontype = jsonobject.has("dimension") ? DimensionType.byName(new ResourceLocation(JSONUtils.getString(jsonobject, "dimension"))) : null;
         Structure<?> structure = jsonobject.has("feature") ? Feature.field_202300_at.get(JSONUtils.getString(jsonobject, "feature")) : null;
         Biome biome = null;
         if (jsonobject.has("biome")) {
            ResourceLocation resourcelocation = new ResourceLocation(JSONUtils.getString(jsonobject, "biome"));
            biome = Registry.field_212624_m.func_218349_b(resourcelocation).orElseThrow(() -> {
               return new JsonSyntaxException("Unknown biome '" + resourcelocation + "'");
            });
         }

         return new LocationPredicate(minmaxbounds$floatbound, minmaxbounds$floatbound1, minmaxbounds$floatbound2, biome, structure, dimensiontype);
      } else {
         return ANY;
      }
   }

   public static class Builder {
      private MinMaxBounds.FloatBound field_218014_a = MinMaxBounds.FloatBound.UNBOUNDED;
      private MinMaxBounds.FloatBound field_218015_b = MinMaxBounds.FloatBound.UNBOUNDED;
      private MinMaxBounds.FloatBound field_218016_c = MinMaxBounds.FloatBound.UNBOUNDED;
      @Nullable
      private Biome field_218017_d;
      @Nullable
      private Structure<?> field_218018_e;
      @Nullable
      private DimensionType field_218019_f;

      public LocationPredicate.Builder func_218012_a(@Nullable Biome p_218012_1_) {
         this.field_218017_d = p_218012_1_;
         return this;
      }

      public LocationPredicate func_218013_a() {
         return new LocationPredicate(this.field_218014_a, this.field_218015_b, this.field_218016_c, this.field_218017_d, this.field_218018_e, this.field_218019_f);
      }
   }
}