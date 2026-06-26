package org.matsim.newMode;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.ReplanningConfigGroup;
import org.matsim.core.config.groups.RoutingConfigGroup;
import org.matsim.core.controler.Controller;
import org.matsim.core.controler.ControllerUtils;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule;
import org.matsim.core.scenario.ScenarioUtils;

public class RunTeleportation1 {
    static void main() {
        Config config = ConfigUtils.loadConfig( "scenarios/equil/config-2026.xml" );

        // your config changes here
        config.controller().setOutputDirectory("output-teleportation1");

        // add bike params
        RoutingConfigGroup.TeleportedModeParams bikeParams = new RoutingConfigGroup.TeleportedModeParams("bike")
                .setBeelineDistanceFactor(1.4)
                .setTeleportedModeSpeed(5.0);
        config.routing().addTeleportedModeParams(bikeParams);

        // add walk params, because if we set bike explicitly, walk params are not there by default
        RoutingConfigGroup.TeleportedModeParams walkParams = new RoutingConfigGroup.TeleportedModeParams("walk")
                .setBeelineDistanceFactor(1.4)
                .setTeleportedModeSpeed(2.0);
        config.routing().addTeleportedModeParams(walkParams);

        // set subtour mode choice as replanning strategy to allow agents to switch to that
        ReplanningConfigGroup.StrategySettings subtourModeChoice = new ReplanningConfigGroup.StrategySettings()
                .setStrategyName(DefaultPlanStrategiesModule.DefaultStrategy.SubtourModeChoice)
                .setWeight(0.1);
        config.replanning().addStrategySettings(subtourModeChoice);

        config.subtourModeChoice().setModes(new String[]{"car", "bike"});

        // scenario loading

        Scenario scenario = ScenarioUtils.loadScenario(config);

        Controller controller = ControllerUtils.createController(scenario);

        controller.run();
    }
}
