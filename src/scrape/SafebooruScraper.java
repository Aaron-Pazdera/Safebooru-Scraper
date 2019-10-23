package scrape;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class SafebooruScraper {

	public static void main(String[] args) {
		String osName = System.getProperty("os.name");
		String filename = getFilename(args, osName);
		String tempFilename = "Scraped_Links.txt";

		try {
			new AttributeDump(new File(tempFilename), AttributeDump.SAMPLE_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Finished downloading links. Resolving duplicates.");
		if (osName.contains("Windows")) {
			System.out.println("Please run GNU Core Utilities on the resulting file.");
			try {
				Path temp = Files.move(Paths.get(tempFilename), Paths.get(filename),
						StandardCopyOption.REPLACE_EXISTING);
				if (temp == null) {
					System.out.println("Failed to move the file.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		resolveDuplicates(tempFilename, filename);

		System.out.println((osName.contains("Windows") ? "Didn't sort. " : "Finished Sorting. ") + "File is ready.");

		// Clean up temp file if not already moved
		if (!osName.contains("Windows")) {
			try {
				Process deleteProcess = Runtime.getRuntime().exec(new String[] { "rm", tempFilename });
				deleteProcess.waitFor();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private static void resolveDuplicates(String tempFilename, String filename) {

		// Call the appropriate command from GNU Core Utilities.
		try {
			Process sortProcess = Runtime.getRuntime()
					.exec(new String[] { "sort", tempFilename, "|", "uniq", ">", filename });
			sortProcess.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	// @nof
	private static String getFilename(String[] args, String osName) {
		return args.length == 0 ? 
			   osName.contains("Windows") ? 
			   "C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\ScrapedSafebooruLinks.txt":
			   "~/Downloads/ScrapedSafebooruLinks.txt" : 
			   args[0];
	}
	// @dof
}
