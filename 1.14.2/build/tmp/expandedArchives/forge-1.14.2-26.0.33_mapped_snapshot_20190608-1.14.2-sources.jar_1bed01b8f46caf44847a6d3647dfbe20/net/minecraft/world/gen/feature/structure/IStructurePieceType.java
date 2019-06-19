package net.minecraft.world.gen.feature.structure;

import java.util.Locale;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.TemplateManager;

public interface IStructurePieceType {
   IStructurePieceType field_214780_a = register(MineshaftPieces.Corridor::new, "MSCorridor");
   IStructurePieceType field_214781_b = register(MineshaftPieces.Cross::new, "MSCrossing");
   IStructurePieceType field_214782_c = register(MineshaftPieces.Room::new, "MSRoom");
   IStructurePieceType field_214783_d = register(MineshaftPieces.Stairs::new, "MSStairs");
   IStructurePieceType field_214784_e = register(PillagerOutpostPieces.PillageOutpost::new, "PCP");
   IStructurePieceType field_214785_f = register(VillagePieces.Village::new, "NVi");
   IStructurePieceType field_214786_g = register(FortressPieces.Crossing3::new, "NeBCr");
   IStructurePieceType field_214787_h = register(FortressPieces.End::new, "NeBEF");
   IStructurePieceType field_214788_i = register(FortressPieces.Straight::new, "NeBS");
   IStructurePieceType field_214789_j = register(FortressPieces.Corridor3::new, "NeCCS");
   IStructurePieceType field_214790_k = register(FortressPieces.Corridor4::new, "NeCTB");
   IStructurePieceType field_214791_l = register(FortressPieces.Entrance::new, "NeCE");
   IStructurePieceType field_214792_m = register(FortressPieces.Crossing2::new, "NeSCSC");
   IStructurePieceType field_214793_n = register(FortressPieces.Corridor::new, "NeSCLT");
   IStructurePieceType field_214794_o = register(FortressPieces.Corridor5::new, "NeSC");
   IStructurePieceType field_214795_p = register(FortressPieces.Corridor2::new, "NeSCRT");
   IStructurePieceType field_214796_q = register(FortressPieces.NetherStalkRoom::new, "NeCSR");
   IStructurePieceType field_214797_r = register(FortressPieces.Throne::new, "NeMT");
   IStructurePieceType field_214798_s = register(FortressPieces.Crossing::new, "NeRC");
   IStructurePieceType field_214799_t = register(FortressPieces.Stairs::new, "NeSR");
   IStructurePieceType field_214800_u = register(FortressPieces.Start::new, "NeStart");
   IStructurePieceType field_214801_v = register(StrongholdPieces.ChestCorridor::new, "SHCC");
   IStructurePieceType field_214802_w = register(StrongholdPieces.Corridor::new, "SHFC");
   IStructurePieceType field_214803_x = register(StrongholdPieces.Crossing::new, "SH5C");
   IStructurePieceType field_214804_y = register(StrongholdPieces.LeftTurn::new, "SHLT");
   IStructurePieceType field_214805_z = register(StrongholdPieces.Library::new, "SHLi");
   IStructurePieceType field_214754_A = register(StrongholdPieces.PortalRoom::new, "SHPR");
   IStructurePieceType field_214755_B = register(StrongholdPieces.Prison::new, "SHPH");
   IStructurePieceType field_214756_C = register(StrongholdPieces.RightTurn::new, "SHRT");
   IStructurePieceType field_214757_D = register(StrongholdPieces.RoomCrossing::new, "SHRC");
   IStructurePieceType field_214758_E = register(StrongholdPieces.Stairs::new, "SHSD");
   IStructurePieceType field_214759_F = register(StrongholdPieces.Stairs2::new, "SHStart");
   IStructurePieceType field_214760_G = register(StrongholdPieces.Straight::new, "SHS");
   IStructurePieceType field_214761_H = register(StrongholdPieces.StairsStraight::new, "SHSSD");
   IStructurePieceType field_214762_I = register(JunglePyramidPiece::new, "TeJP");
   IStructurePieceType field_214763_J = register(OceanRuinPieces.Piece::new, "ORP");
   IStructurePieceType field_214764_K = register(IglooPieces.Piece::new, "Iglu");
   IStructurePieceType field_214765_L = register(SwampHutPiece::new, "TeSH");
   IStructurePieceType field_214766_M = register(DesertPyramidPiece::new, "TeDP");
   IStructurePieceType field_214767_N = register(OceanMonumentPieces.MonumentBuilding::new, "OMB");
   IStructurePieceType field_214768_O = register(OceanMonumentPieces.MonumentCoreRoom::new, "OMCR");
   IStructurePieceType field_214769_P = register(OceanMonumentPieces.DoubleXRoom::new, "OMDXR");
   IStructurePieceType field_214770_Q = register(OceanMonumentPieces.DoubleXYRoom::new, "OMDXYR");
   IStructurePieceType field_214771_R = register(OceanMonumentPieces.DoubleYRoom::new, "OMDYR");
   IStructurePieceType field_214772_S = register(OceanMonumentPieces.DoubleYZRoom::new, "OMDYZR");
   IStructurePieceType field_214773_T = register(OceanMonumentPieces.DoubleZRoom::new, "OMDZR");
   IStructurePieceType field_214774_U = register(OceanMonumentPieces.EntryRoom::new, "OMEntry");
   IStructurePieceType field_214775_V = register(OceanMonumentPieces.Penthouse::new, "OMPenthouse");
   IStructurePieceType field_214776_W = register(OceanMonumentPieces.SimpleRoom::new, "OMSimple");
   IStructurePieceType field_214777_X = register(OceanMonumentPieces.SimpleTopRoom::new, "OMSimpleT");
   IStructurePieceType field_214778_Y = register(OceanMonumentPieces.WingRoom::new, "OMWR");
   IStructurePieceType field_214779_Z = register(EndCityPieces.CityTemplate::new, "ECP");
   IStructurePieceType field_214751_aa = register(WoodlandMansionPieces.MansionTemplate::new, "WMP");
   IStructurePieceType field_214752_ab = register(BuriedTreasure.Piece::new, "BTP");
   IStructurePieceType field_214753_ac = register(ShipwreckPieces.Piece::new, "Shipwreck");

   StructurePiece load(TemplateManager p_load_1_, CompoundNBT p_load_2_);

   static IStructurePieceType register(IStructurePieceType p_214750_0_, String p_214750_1_) {
      return Registry.register(Registry.field_218362_C, p_214750_1_.toLowerCase(Locale.ROOT), p_214750_0_);
   }
}