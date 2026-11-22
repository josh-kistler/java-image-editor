Java Image Editor
A desktop image-processing application developed in Java for CSCI-C212: Introduction to Software Systems at Indiana University Bloomington (Spring 2025). The project implements pixel-level image transformations and PPM image file handling within a provided Java Swing interface. The same course submission also includes a separate command-line program that retrieves and displays weather forecasts from the Open-Meteo API.
Features
Image editor
- PPM image loading: Reads basic ASCII PPM (P3) image data into a BufferedImage.
- Remove red channel: Sets the red component of each pixel to zero.
- Grayscale: Averages red, green, and blue channel values to produce grayscale pixels.
- Invert colors: Replaces each RGB channel value with 255 - value.
- Mirror: Flips an image horizontally or vertically.
- Rotate: Implements 90-degree clockwise and counterclockwise transformations.
- Repeat: Tiles an image horizontally or vertically a user-selected number of times.
- Desktop controls: Uses the supplied Swing interface for file selection, menus, undo/redo, and zooming.
Compatibility note: Grayscale and rotation have coordinate/dimension bugs for some non-square images. PPM export is implemented but currently writes an invalid PPM header and missing pixel separators. See Known limitations.
Weather forecast CLI
- Uses Java's HttpURLConnection to send HTTP GET requests to the Open-Meteo Forecast API.
- Parses JSON forecast responses with the Gson library.
- Prints hourly temperature predictions sampled at three-hour intervals for the API's seven-day forecast window.
- Defaults to Bloomington, Indiana, with configurable latitude, longitude, and temperature units through command-line flags.
Technologies
- Java — application logic, file I/O, and HTTP requests
- Java Swing / AWT — supplied graphical image-editor interface and BufferedImage operations
- PPM (P3) — plain-text RGB image format
- HTTP / REST API — weather forecast retrieval
- JSON / Gson 2.10.1 — parsing weather data
Getting started
Requirements
- A Java Development Kit (JDK). The submitted code was compiled successfully with JDK 21.
- A desktop environment to display the Swing image editor.
- An internet connection to use the weather forecast program.
Gson 2.10.1 is included in lib/ in the supplied project files. The project uses direct Java compilation rather than a Maven or Gradle build file.
Compile
From the directory containing src/ and lib/:
mkdir -p build
javac -cp lib/gson-2.10.1.jar -d build src/*.java
Run the image editor
java -cp build ImageEditorRunner
Use File > Open to load the included pm.ppm example image, then choose a transformation from Tools. Image zoom and undo/redo controls are provided by the course's starter interface.

Note: The current Save As implementation does not produce valid PPM output. Do not rely on saved files until the export routine is corrected.
Run the weather forecast program
On macOS or Linux:
java -cp "build:lib/gson-2.10.1.jar" WeatherForecast
To specify another location and temperature unit:
java -cp "build:lib/gson-2.10.1.jar" WeatherForecast --latitude 36.0689 --longitude -79.8102 --unit C
On Windows, replace the : between classpath entries with ;:
java -cp "build;lib/gson-2.10.1.jar" WeatherForecast
The supported flags are --latitude, --longitude, and --unit (F or C). The program defaults to the coordinates of Bloomington, Indiana, and Fahrenheit. Known issue: The output suffix is currently °F even when requesting Celsius data.
Project structure
.
├── src/
│   ├── ImageEditorRunner.java    # Image editor entry point
│   ├── ImageEditor.java          # Image state and PPM read/write methods
│   ├── ImageOperations.java      # Pixel-level transformations
│   ├── ColorOperations.java      # Starter RGB utility methods
│   ├── ToolsMenu.java           # Enables transformation menu items
│   ├── WeatherForecast.java     # Weather API command-line program
│   └── ...                      # Additional supplied GUI/menu classes
├── lib/
│   └── gson-2.10.1.jar          # JSON dependency
└── pm.ppm                       # Course-provided example image
Implementation notes
Image transformations iterate over source pixels and construct new BufferedImage objects. RGB-based effects compute replacement channel values, while geometric effects remap pixel coordinates or tile the source image. The editor stores image versions in stacks to support undo/redo through the provided interface.

The companion weather program constructs a parameterized Open-Meteo URL, checks the HTTP response status, reads the response body, parses the hourly.time and hourly.temperature_2m arrays, and displays sampled forecasts in the console.
Known limitations
This repository preserves the coursework submission and has not been presented as a fully polished production application.
- PPM export needs repair: writePpmImage() concatenates width and height without a separator, omits the maximum color value (255), and does not consistently separate adjacent RGB triples. Files produced by this method do not conform to the expected P3 layout.
- Non-square image handling: grayscale() uses width/height in the wrong loop order, and rotate() allocates an output image without swapping dimensions. Both can fail for rectangular images.
- Limited PPM parsing: The importer assumes a simple P3 file layout and does not account for comments or arbitrary header formatting.
- Weather unit label: Celsius requests retrieve Celsius data but the output prints a Fahrenheit symbol. The forecast uses a fixed EST time-zone parameter, so it does not automatically adapt to the requested location.
- Runtime verification: The Java source compiled successfully with JDK 21, and PPM loading and several image transformations were exercised locally. A live weather API response and the graphical interface were not fully verified in this review.
Contributors and attribution
Created by Josh Kistler and Akhil Guttikonda as a two-person course project for Indiana University Bloomington, CSCI-C212 (Spring 2025).

The team divided work between the image editor and API connectivity portions and collaborated through in-person work sessions and teaching-assistant office hours. The provided submission does not specify which teammate completed each portion, so individual ownership is not attributed here.

Starter-code credit: The course supplied the image editor's GUI, menus, and supporting components. This repository builds on that starter project; it does not claim the complete desktop interface was designed from scratch by the contributors.
