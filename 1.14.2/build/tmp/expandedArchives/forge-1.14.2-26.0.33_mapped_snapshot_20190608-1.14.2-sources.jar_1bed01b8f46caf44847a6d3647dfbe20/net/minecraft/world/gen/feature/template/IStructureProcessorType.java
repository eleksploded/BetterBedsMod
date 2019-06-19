package net.minecraft.world.gen.feature.template;

import net.minecraft.util.IDynamicDeserializer;
import net.minecraft.util.registry.Registry;

public interface IStructureProcessorType extends IDynamicDeserializer<StructureProcessor> {
   IStructureProcessorType field_214920_b = func_214917_a("block_ignore", BlockIgnoreStructureProcessor::new);
   IStructureProcessorType field_214921_c = func_214917_a("block_rot", IntegrityProcessor::new);
   IStructureProcessorType field_214922_d = func_214917_a("gravity", GravityStructureProcessor::new);
   IStructureProcessorType field_214923_e = func_214917_a("jigsaw_replacement", (p_214919_0_) -> {
      return JigsawReplacementStructureProcessor.field_215196_a;
   });
   IStructureProcessorType field_214924_f = func_214917_a("rule", RuleStructureProcessor::new);
   IStructureProcessorType field_214925_g = func_214917_a("nop", (p_214918_0_) -> {
      return NopProcessor.INSTANCE;
   });

   static IStructureProcessorType func_214917_a(String p_214917_0_, IStructureProcessorType p_214917_1_) {
      return Registry.register(Registry.field_218364_E, p_214917_0_, p_214917_1_);
   }
}