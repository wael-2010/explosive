package rainy.explosive;

import java.util.ArrayList;
import java.util.List;

public class Detenatorcheck {
    private static final List<Runnable> waitlist = new ArrayList<>();

    public static void queue(Runnable effect) {
        waitlist.add(effect);
   }

   public static void activate() {
        for (Runnable effect : waitlist) {
            effect.run();
        }
        waitlist.clear();
   }
}
