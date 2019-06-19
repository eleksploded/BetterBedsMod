package net.minecraft.util;

public class ActionResult<T> {
   private final ActionResultType field_188399_a;
   private final T result;

   public ActionResult(ActionResultType typeIn, T resultIn) {
      this.field_188399_a = typeIn;
      this.result = resultIn;
   }

   public ActionResultType getType() {
      return this.field_188399_a;
   }

   public T getResult() {
      return this.result;
   }

   //Just a generic helper function to make typecasing easier...
   public static <T> ActionResult<T> newResult(ActionResultType result, T value) {
      return new ActionResult<T>(result, value);
   }
}