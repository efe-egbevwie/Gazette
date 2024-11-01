# Gazette

An Android application that fetches news from different categories from the New York Times APi and
saves them locally

## Installation
Builds are available under the github repository releases 

## ScreenShots

### Phone Home screen day mode

<img src=screenshots/day/home_screen_day.png width="20%" height="20%">

### Phone Home screen dark mode

<img src=screenshots/night/home_screen_night.png width="20%" height="20%">

### Create collection

<img src="screenshots/day/create_collection_day.png" width="20%" height="20%">

### Save story to collection

<img src="screenshots/day/save_story_day.png" width="20%" height="20%">

### Delete story from  collection

<img src="screenshots/day/delete_from_collection.png" width="20%" height="20%">

### Browse read later collections

<img src="screenshots/day/read_later_day.png" width="20%" height="20%">

### Browse stories in  collections

<img src="screenshots/day/browse_collection_day.png" width="20%" height="20%">

### News detail

<img src="screenshots/day/news_detail_day.png" width="20%" height="20%">

## Architecture

This app is a single module project with a single activity and multiple fragments.
Navigation is done using the jetpack navigation library.
Fragments serve as containers for composable screen content.
Safe args is used to pass arguments between fragments.
Jetpack view model holds each screen state in the form of a MutableStateFlow. Screens observe this
flow
and reacts to it's state changes, they also pass events to the view model which in turn manipulates
the
app data.

## Android components used

* Views
* Jetpack Compose
* Room persistence library
* Jetpack ViewModel
* Compose ConstraintLayout
* Kotlin Coroutines
* Jetpack Navigation

## Libraries used

* Retrofit and OkHttp
* Kotlinx serialization
* Expandable Bottom bar -  https://github.com/st235/ExpandableBottomBar

## Contribution

To make a contribution, just make a pull-request.

## To-DO

* Edit read later collection titles

## License

The MIT License (MIT) Copyright © 2022 Efe Egbevwie

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the “Software”), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


 
