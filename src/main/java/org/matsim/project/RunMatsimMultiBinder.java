package org.matsim.project;

import com.google.inject.Inject;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;
import org.apache.commons.math.genetics.Population;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.contrib.otfvis.OTFVisLiveModule;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.io.IOUtils;
import org.matsim.examples.ExamplesUtils;

import java.util.Map;
import java.util.Set;

public class RunMatsimMultiBinder {
    public static void main(String[] args) {
        var url = IOUtils.extendUrl(ExamplesUtils.getTestScenarioURL("equil"), "config.xml");
        var config = ConfigUtils.loadConfig(url);
        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
        config.controler().setLastIteration(1);

        Scenario scenario = ScenarioUtils.loadScenario(config);
        var controler = new Controler(scenario);

        controler.addOverridingModule(new AbstractModule() {
            @Override
            public void install() {
                this.addEventHandlerBinding().to(MyEventHandler.class);
                this.bind(MyHelper.class).to(MyHelperImpl1.class);

                Multibinder<MyHelper> myHelperMultibinder = Multibinder.newSetBinder(this.binder(), MyHelper.class);
                myHelperMultibinder.addBinding().to(MyHelperImpl1.class);
            }
        });

        controler.addOverridingModule(new AbstractModule() {
            @Override
            public void install() {
                Multibinder<MyHelper> myHelperMultibinder = Multibinder.newSetBinder(this.binder(), MyHelper.class);
                myHelperMultibinder.addBinding().to(MyHelperImpl2.class);
                myHelperMultibinder.permitDuplicates();

                MapBinder<String, MyHelper> stringMyHelperMapBinder = MapBinder.newMapBinder(this.binder(), String.class, MyHelper.class);
                stringMyHelperMapBinder.addBinding("one").to(MyHelperImpl1.class);
                stringMyHelperMapBinder.addBinding("two").to(MyHelperImpl2.class);

            }
        });

        controler.addOverridingModule(new OTFVisLiveModule());

        controler.run();
    }

    private static class MyEventHandler implements LinkLeaveEventHandler {
        @Inject
        Set<MyHelper> myHelpers;

        @Inject
        Map<String,MyHelper> helperMap;

        @Override
        public void handleEvent(LinkLeaveEvent linkLeaveEvent) {
            //System.out.println("LinkLeaveEvent: " + linkLeaveEvent);
            helperMap.get("one").doSomething();
        }
    }

    private interface MyHelper {
        void doSomething();
    }

    private static class MyHelperImpl1 implements MyHelper{

        @Override
        public void doSomething() {
            System.out.println("Here I am 1.");
        }
    }

    private static class MyHelperImpl2 implements MyHelper{

        @Override
        public void doSomething() {
            System.out.println("Here I am 2.");
        }
    }
}
