# What's gwt-ar

gwt-ar is a library made for GWT. It provides an API to use augmented reality in a browser.

# Code sample

The API is designed to leave you only to worry about your POIs.

```java
ARPanel arPanel = new ARPanel();
arPanel.addMarker(new DefaultMarker("Chatelet", new Location(48.858673, 2.347426, 0.0)));
arPanel.addMarker(new DefaultMarker("Hôtel de ville", new Location(48.857494, 2.351460, 0.0)));
arPanel.addMarker(new DefaultMarker("Cité", new Location(48.855525, 2.347695, 0.0)));
arPanel.setSize("800px", "600px");
RootPanel.get().add(arPanel);
```

# Support

gwt-ar relies on the Camera , Geolocation and Device Orientation HTML5 API. Theses API are currently supported by :
* Chrome 21.0 +
* Opera 12.5 +
* Opera mobile 12.0 +