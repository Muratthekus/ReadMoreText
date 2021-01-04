# **Read More Text View**
A custom text view with abilities to trim text according to the length or line value

[![](https://jitpack.io/v/Muratthekus/ReadMoreText.svg)](https://jitpack.io/#Muratthekus/ReadMoreText)

### Usage
```xml
<me.thekusch.view.ReadMoreTextView
    android:id="@+id/readMoreTextView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:anchorPoint=""
    app:textMode=""
    app:isExpanded=""
    app:readMoreText=""
    app:readLessText=""/>
```

### Implementation
```gradle
allprojects {
	repositories {
	    ...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
        implementation 'com.github.Muratthekus:ReadMoreText:v1.0'
}
```

#### Result
![results](https://user-images.githubusercontent.com/45212967/103579666-c2f33f80-4ee9-11eb-82f4-beffaef64f7a.gif)


```
MIT License

Copyright (c) 2021 Murat Kuş

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```