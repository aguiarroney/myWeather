# myWeather

Android application that provides infromation about weather.
This project is part of a udacity nanodegree program.

## Rubric

- This project contains three screens witch are controlled by a Navigation Controller;
- All the screens adhere to Android standards and display appropriately on screens of different size and resolution;
- There is an animation on each screen implemented with MotionLayout;
- The App works with infomation about weather witch are provided by the API [OpenWeather](https://openweathermap.org/).
	- The API is called at the main fragment searching for the wather of the phone current location;
	- The API is also called at the find fragment, where the user can search for a new place and get its wather.
- The App saves favourie locations and its respective wather in a local database implemented with RoomDatabase;
	- After seaching for a new place at the find fragment, the user can save it at the local storage and consult it at the favourites fragment.
- This project follows the patterns of an MVVM architecture;
- Last, but not list, the application requests permissions and check for device settings in order to get GPS location;
