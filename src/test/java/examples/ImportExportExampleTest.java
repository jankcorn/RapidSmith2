/*
 * Copyright (c) 2017 Brigham Young University
 *
 * This file is part of the BYU RapidSmith Tools.
 *
 * BYU RapidSmith Tools is free software: you may redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * BYU RapidSmith Tools is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * A copy of the GNU General Public License is included with the BYU
 * RapidSmith Tools. It can be found at doc/LICENSE.GPL3.TXT. You may
 * also get a copy of the license at <http://www.gnu.org/licenses/>.
 */
package examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.byu.ece.rapidSmith.RSEnvironment;
import edu.byu.ece.rapidSmith.examples.ImportExportExample;

/**
 * jUnit test for the ImportExportExample class in RapidSmith2
 * @author Dallon Glick
 */
public class ImportExportExampleTest {
	private static ImportExportExample example;
	private static final Path testDirectory = RSEnvironment.defaultEnv().getEnvironmentPath()
			.resolve("src")
			.resolve("test")
			.resolve("resources")
			.resolve("ImportTests");
	private final static PrintStream stdout = System.out;

	/**
	 * Initializes the ImportExportExample test.
	 * @throws IOException 
	 */
	@BeforeAll
	public static void initializeTest() throws IOException {
		// Get a checkpoint to use. No others are needed to test the example program functionality.
		String checkpointIn = testDirectory.resolve("RSCP").resolve("artix7").resolve("count16.rscp").toString();
		String checkpointOut = testDirectory.resolve("tmp.tcp").toString();
		
		// Get an ImportExportExample to use.
		example = new ImportExportExample(checkpointIn, checkpointOut);
		
		// Change the standard output stream so ImportExportExample doesn't print anything
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream tempOutput = new PrintStream(bos, true);
		System.setOut(tempOutput);
	}

	/**
	 * Cleans up after the ImportExportExample test.
	 */
	@AfterAll
	public static void cleanupTest() {		
		// Delete the generated TCP
		deleteTemporaryFiles();
		
		// Change standard output stream back to stdout
		System.setOut(stdout);
	}

	/**
	 * Tests that the example program runs to completion without any exceptions occurring.
	 * @throws IOException
	 */
	@Test
	@DisplayName("ImportExportExample Run Test")
	public void runToCompletion() throws IOException  {
		example.importExportDesign();
	}
	
	/**
	 * Deletes the exported TCP directory.
	 */
	private static void deleteTemporaryFiles() {
		try {
			// delete directory with output TCP
			Files.walkFileTree(testDirectory.resolve("tmp.tcp"), new SimpleFileVisitor<Path>() {
			   @Override
			   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			       Files.delete(file);
			       return FileVisitResult.CONTINUE;
			   }
	
			   @Override
			   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			       Files.delete(dir);
			       return FileVisitResult.CONTINUE;
			   }
			});
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
