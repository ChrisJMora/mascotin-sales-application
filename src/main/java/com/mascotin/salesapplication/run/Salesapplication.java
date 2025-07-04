package com.mascotin.salesapplication.run;

import org.openxava.util.*;

/**
 * Execute this class to start the application.
 *
 * With OpenXava Studio/Eclipse: Right mouse button &gt; Run As &gt; Java Application
 */
@SuppressWarnings({"PMD.UseUtilityClass","PMD.SignatureDeclareThrowsException"})
public final class Salesapplication {

	public static void main(final String[] args) throws Exception {
		DBServer.start("salesapplication-db"); // To use your own database comment this line and configure src/main/webapp/META-INF/context.xml
		AppServer.run("salesapplication"); // Use AppServer.run("") to run in root context
	}

}
