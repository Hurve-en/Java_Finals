package concert.util;

import concert.entity.Fan;
import concert.entity.RegularFan;
import concert.entity.VipFan;
import concert.model.Stadium;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FanFactory {

    private static final Random random = new Random();

    public static List<Fan> createFans(int count, Stadium stadium) {
        List<Fan> fans = new ArrayList<>();

        int vipCount = count / 5; // about 20% VIPs

        for (int i = 1; i <= count; i++) {
            String name;
            if (i <= vipCount) {
                name = "VIPFan-" + i;
                fans.add(new VipFan(name, stadium));
            } else {
                name = "Fan-" + i;
                fans.add(new RegularFan(name, stadium));
            }
        }

        return fans;
    }
}
