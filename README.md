# What's gwt-ar

gwt-ar is a library made for GWT. It provides an API to use augmented reality in a browser.

# Code sample

The API is designed to leave you only to worry about your POIs.

```java
ARPanel arPanel = new ARPanel();
arPanel.setSize("800px", "600px");
RootPanel.get().add(arPanel);

arPanel.addMarker(new InformationMarker(new Location(48.858673, 2.347426, 0.0), "Métro", "Chatelet");
arPanel.addMarker(new InformationMarker(new Location(48.857494, 2.351460, 0.0), "Métro", "Hôtel de ville");
arPanel.addMarker(new InformationMarker(new Location(48.855525, 2.347695, 0.0), "Métro", "Cité");
```

# Support

gwt-ar relies on the Camera, Geolocation and Device Orientation HTML5 API which aren't supported by all browser. Currently gwt-ar is fully supported and tested on : 
* Opera mobile 12.0 +

# Demo

[http://wikilocation-ar.cloudfoundry.com/ This site] displays geolocalized information from a 2km radius around your location.