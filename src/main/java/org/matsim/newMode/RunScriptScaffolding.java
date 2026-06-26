package org.matsim.newMode;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.ReplanningConfigGroup;
import org.matsim.core.config.groups.RoutingConfigGroup;
import org.matsim.core.controler.Controller;
import org.matsim.core.controler.ControllerUtils;
import org.matsim.core.scenario.ScenarioUtils;

public class RunScriptScaffolding {
    static void main() {
        Config config = ConfigUtils.loadConfig( "scenarios/equil/config-2026.xml" );

        /**
         * things to do for stage 1 new mode:
         * - add a new replanning strategy SubtourModeChoice (enable mode choice)
         * - add teleportation parameters for bike
         */

        ReplanningConfigGroup.StrategySettings subtourModeChoice = new ReplanningConfigGroup.StrategySettings()
                .setStrategyName("SubtourModeChoice").setWeight(0.1);
        config.replanning().addStrategySettings(subtourModeChoice);
        config.subtourModeChoice().setModes(new String[]{"car", "bike"});

        // routing parameters
        RoutingConfigGroup.TeleportedModeParams bikeParams = new RoutingConfigGroup.TeleportedModeParams("bike");
        bikeParams.setBeelineDistanceFactor(1.4);
        bikeParams.setTeleportedModeSpeed(3.04);
        config.routing().addTeleportedModeParams(bikeParams);

        RoutingConfigGroup.TeleportedModeParams walkParams = new RoutingConfigGroup.TeleportedModeParams("walk");
        walkParams.setBeelineDistanceFactor(1.4);
        walkParams.setTeleportedModeSpeed(1.04);
        config.routing().addTeleportedModeParams(walkParams);

        Scenario scenario = ScenarioUtils.loadScenario(config);

        Controller controller = ControllerUtils.createController(scenario);

        controller.run();
    }
}
