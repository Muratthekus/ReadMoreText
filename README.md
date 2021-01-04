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



