## Dog Image Generator App

This repository contains an Android app that allows users to generate random images of dogs and save them for viewing later. The app features three screens - Home Screen, Generate Dogs Screen, and My Recently Generated Dogs Screen - providing a seamless user experience.

# Features
# Home Screen
* Simple and intuitive home screen with buttons to navigate to the other two screens.
#Generate Dogs Screen
* A "Generate!" button that sends a request to a public dog images AP.
* When the "Generate!" button is pressed and the image data is successfully fetched, it is displayed to the user and stored in a cache.
* The cache utilizes an LRU (Least Recently Used) cache strategy, holding the 20 most recent image data generated from requests to the API.
* The cache persists between app sessions, ensuring that previously generated images can be viewed later even if the app is closed and reopened.
# My Recently Generated Dogs Screen
* A visually appealing scrollable gallery that displays the recently generated dog images stored in the cache.
* Users can view their previously generated dog images in a gallery format for a delightful experience.
* The screen includes a "Clear Dogs" button that allows users to clear out the cache, removing all the generated dog images from the gallery.

# Technologies Used
* Kotlin: The app is written in Kotlin, taking advantage of its modern and concise features.
* Retrofit: Used to communicate with the public dog images API and fetch random dog images.
* LRU Cache: An LRU cache strategy is implemented to store and manage the most recent dog image data efficiently.
* Android Room Database: Used for persistent storage of the cache, ensuring that images persist between app sessions.
* Android RecyclerView: Utilized to create a smooth and scrollable gallery of the recently generated dog images.
