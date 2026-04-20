/* *********************************************************************** *
 * project: org.matsim.*												   *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package org.matsim.other;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.ControllerConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy.OverwriteFileSetting;
import org.matsim.core.scenario.ScenarioUtils;

import java.util.Set;

/**
 * @author nagel
 *
 */
public class RunMatsimWithoutApplication {

	public static void main(String[] args) {

		Config config;
		if ( args==null || args.length==0 || args[0]==null ){
			config = ConfigUtils.loadConfig( "scenarios/equil/config-2026.xml" );
		} else {
			config = ConfigUtils.loadConfig( args );
		}

		config.controller().setOverwriteFileSetting( OverwriteFileSetting.deleteDirectoryIfExists );

        // possibly modify config here

		// Ex. 2, Task 3: changing the output directory, last iteration and compression type
		config.controller().setLastIteration(0);
		config.controller().setOutputDirectory("my-output-folder");
		config.controller().setCompressionType(ControllerConfigGroup.CompressionType.gzip);
		
		Scenario scenario = ScenarioUtils.loadScenario(config) ;

		// possibly modify scenario here

		// Ex. 2, Bonus Task: setting the freespeed low links 2, 3, 4, 11, 12, 13 to 1m/s
		Set<String> linksToChange = Set.of("2", "3", "4", "11", "12", "13");
		for (String link : linksToChange) {
			scenario.getNetwork().getLinks().get(Id.createLinkId(link)).setFreespeed(1.0);
		}

		Controler controler = new Controler( scenario ) ;
		
		// possibly modify controler here

//		controler.addOverridingModule( new OTFVisLiveModule() ) ;

//		controler.addOverridingModule( new SimWrapperModule() );
		
		// ---
		
		controler.run();
	}
	
}
