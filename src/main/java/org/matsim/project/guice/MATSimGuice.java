package org.matsim.project.guice;

import com.google.inject.Injector;
import com.google.inject.Module;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.*;
import org.matsim.core.controler.corelisteners.ControlerDefaultCoreListenersModule;
import org.matsim.core.scenario.ScenarioByInstanceModule;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MATSimGuice {
    public static void main(String[] args) {
        Config config = ConfigUtils.createConfig();
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);

        Scenario scenario = ScenarioUtils.createScenario(config);

        final Collection<Module> theModules = new ArrayList<>();
        theModules.add( new AbstractModule(){
            @Override
            public void install(){
                install( new NewControlerModule() );
                install( new ControlerDefaultCoreListenersModule() );
                install( new ControlerDefaultsModule() );
                install( new ScenarioByInstanceModule(scenario) );
            }
        });
        theModules.addAll( Arrays.asList(new Module[]{}) );

        Injector injector = org.matsim.core.controler.Injector.createInjector(config, theModules.toArray(new Module[0]));

        Controler controler = new Controler(scenario);
        controler.run();


    }
}
