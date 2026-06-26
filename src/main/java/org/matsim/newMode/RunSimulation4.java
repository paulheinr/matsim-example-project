package org.matsim.newMode;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.QSimConfigGroup;
import org.matsim.core.config.groups.ReplanningConfigGroup;
import org.matsim.core.controler.Controller;
import org.matsim.core.controler.ControllerUtils;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.replanning.strategies.DefaultPlanStrategiesModule;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.Set;

public class RunSimulation4 {
    static void main() {
        Config config = ConfigUtils.loadConfig( "scenarios/equil/config-2026.xml" );

        // your config changes here
        config.controller().setOutputDirectory("output-simulation4");
        config.controller().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);

        // add bike params
        config.routing().setNetworkModes(Set.of("car", "bike"));

        config.routing().removeTeleportedModeParams("bike");

        // set subtour mode choice as replanning strategy to allow agents to switch to that
        ReplanningConfigGroup.StrategySettings subtourModeChoice = new ReplanningConfigGroup.StrategySettings()
                .setStrategyName(DefaultPlanStrategiesModule.DefaultStrategy.SubtourModeChoice)
                .setWeight(0.1);
        config.replanning().addStrategySettings(subtourModeChoice);

        config.subtourModeChoice().setModes(new String[]{"car", "bike"});

        // setting the vehicle source to mode vehicle types in order to use the vehicle type created in the vehicles
        config.qsim().setVehiclesSource(QSimConfigGroup.VehiclesSource.modeVehicleTypesFromVehiclesData);
        config.qsim().setMainModes(Set.of("car", "bike"));

        // scenario loading

        Scenario scenario = ScenarioUtils.loadScenario(config);

        // add bike as allowed mode on all links
        for (Link link : scenario.getNetwork().getLinks().values()) {
            NetworkUtils.addAllowedMode(link, "bike");
        }

        scenario.getVehicles().addModeVehicleType("car").setNetworkMode("car");
        scenario.getVehicles().addModeVehicleType("bike").setNetworkMode("bike").setMaximumVelocity(5.0);

        Controller controller = ControllerUtils.createController(scenario);

        controller.run();
    }
}
