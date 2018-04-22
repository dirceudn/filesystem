# File system

[![Build Status](https://travis-ci.org/anfederico/Clairvoyant.svg?branch=master)](https://travis-ci.org/anfederico/Clairvoyant)

## Basic Overview

 This app will give the user access to a "file system" that can be accessed using an HTTP REST API.The endpoint for this     exercise is located at http://private-2c2b2-nodes4.apiary-mock.com/nodes.

![](header.png)

## Implemented Features

* Mandatory
  - Navigate "file system" 
  - List directory contents 
  - Enter directories 
  - Return to parent directory 
  - View media files (png, jpg, mp3, mp4) 
  - Pull to refresh 
  
* Additional
  - Read other media types (pdf, txt, xls, ppt, gif, ...) 
  - Open files in other application 
  - Search through current directory content 
  - Share file url with friends

## App Architecture

Here is an architecture diagram of the system. The first one is an overview of the components using dagger 2 and the second is a mvp design.

![alt text](https://github.com/dirceudn/filesystem/blob/master/filesystem.png)

-----

![alt text](https://github.com/dirceudn/filesystem/blob/master/filesystemMvp(1).png)


## Libraries and technical decisions

### Glider
The glider library in terms of optimization is superior to picasso and still supports animated gifs.Also, because of Glide integrates with the activity life-cycle, animated GIFs are also paused in onStop() to avoid draining the battery in the background. I used the library in my app without handle with large images and loading.
#### Some features that Glide support but not Picasso:
* Animated GIF support
* Thumbnail support
* Configurations & Customization


```java
 //TODO implements the GlideImageLoader to handle with large images, progress and cache.
        Glide.with(this)
                .load(Objects.requireNonNull(imageNode).getUrl())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
```

### Media Player (Audio) Native SDK

 I use media player to play single instance of sound. Its avoid multiple instances of sound if the user clicks more than once for example 

```java
public class MediaPlayerManager {

    MediaPlayer audioPlayer;
    private static MediaPlayerManager instance = null;

    private MediaPlayerManager() {
    }

     static MediaPlayerManager getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerManager.class) {
                if (instance == null) {
                    instance = new MediaPlayerManager();
                }
            }
        }

        return instance;
    }
}
```
### TODO (Audio Player) 
 * Play a list of songs in order or random
 * Create an option to repate the song
 * Save the music to play offline
 
 ### TODO (Video Player) 
 * Implements the custom video controller
 * Save the video to play offline
 * Add options like subtitle, sound control screencast.
 
 
 ### TODO (Image Player) 
 * Implements a gallery to show all image on gride formart
 * Create an image slide show
 * Set image like wallpaper
 * Edit image (resize, color, filter)
 * Save image offline
 
## Contributing

1. Fork it (https://github.com/dirceudn/filesystem.git)
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request

## Libraries

* [RxJava 2](https://github.com/ReactiveX/RxJava)
* [Dagger 2](https://github.com/google/dagger)
* [Retrofit](https://github.com/square/retrofit) + [OkHttp](https://github.com/square/okhttp)
* [Glide](https://github.com/bumptech/glide)
* UI: RecyclerView, ConstraintLayout.

## License

MIT

