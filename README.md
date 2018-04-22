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

A few motivating and useful examples of how your product can be used. Spice this up with code blocks and potentially more screenshots.

_For more examples and usage, please refer to the [Wiki][wiki]._

## Libraries and technical decisions


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

