# Vortex JavaScript Editor

JavaScript livecoding editor for the Eclipse IDE.  

Licensed under the [MIT License](http://www.opensource.org/licenses/mit-license.php).


##About

The Vortex JavaScript Editor is a Eclipse editor plugin that holds a WebSocket connection to your browser and evalutates changed code on key press. This shortens the feedback loop and can speed up some JavaScript tasks.

The editor is build ontop of the JavaScript editor from the [Eclipse Web Tools Platform](http://www.eclipse.org/webtools/).
The WTP JavaScript Editor is a full blown JavaScript editor were the Vortex editor only hooks into its document change event. The WTP JavaScript Editor is not included in the Vortex project and has to be pre-installed.

The communication with the browser is done via [Jetty](http://www.eclipse.org/jetty/) featured WebSockets. The required Jetty libraries from version 8.1.7 are included into the project.

Inside the browser the [Vortex JavaScript Connector](https://github.com/kristophjunge/vortex-connector) acts as WebSocket client and receives changed documents which then can be evalutated.

Livecoding is a complex topic and this editor is only an experiment for now.


## Installation

Requirements:
* Eclipse Juno installation
* Eclipse plugin "JavaScript Development Tools" (For me working with version 1.4.1)

Download the plugin JAR from [here](http://dev.kristophjunge.com/vortex-editor/build/latest/) and place it into the plugin folder of your Eclipse installation.


## Usage

Once the plugin is installed successfully the entry "Vortex JavaScript Editor" should appear under the list of editors associated with the "js" file extension. Just open a JavaScript file and the server will be started.

To connect with the browser you have to run the [Vortex JavaScript Connector](https://github.com/kristophjunge/vortex-connector) inside the browser.

I created a [screencast]() that shows the editor in action.


## Documentation

There is an inline code documentation.


## Build

The Editor is a Eclipse plugin that can be edited and build with the [Eclipse PDE](http://www.eclipse.org/pde/) (Plugin Development Environment).