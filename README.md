# Simple Tweet

**Simple Tweet** is an android app that allows a user to view his Twitter timeline. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

## User Stories

- User can sign in to Twitter using OAuth login
- User can view tweets from their home timeline
  - User is displayed the username, name, and body for each tweet
  - User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet i.e. "8m", "7h"

- User can compose and post a new tweet
  - User can click a Compose icon in the Action Bar on the top right
  - User can then enter a new tweet and post this to twitter
  - User is taken back to home timeline with the new tweet visible in their timeline
  - Newly created tweet is be manually inserted into the timeline and does not rely on a full refresh
  - User can see a counter with total number of characters left for the tweet on the compose tweet page

- User can pull down to refresh the tweets timeline
- User is using "Twitter branded" colors and styles

- Uses Parcelable instead of Serializable using the Parceler library.
- Replaced all icon drawables and other static image assets with vector drawables where appropriate.
- On the Twitter timeline, leveraged the CoordinatorLayout to apply scrolling behavior that hides / shows the toolbar

- Compose buttons placed directly on toolbar(s).

## Gif Walkthrough

<img src='https://media.giphy.com/media/ddQsUpajS6bllxyx2d/giphy.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Ended up losing a lot of time when working to implement the scrolling behavior for the toolbar, simply because I didn't understand that the CoordinatorLayout was meant to replace the ConstraintLayout and not layer within it.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright [2019] [Janeth Delgado]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
