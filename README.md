# PaintingView2

目前releaseVersion為 **v1.0.2**
build.gradle
```
allprojects {
  repositories {
....
	maven { url 'https://jitpack.io' }
  }
}
```
  
  
```
  dependencies {
	        implementation 'com.github.uuko:PaintingView2:releaseVersion'
	}
```


使用方式
```
    var it=Intent(this,PaintItActivity::class.java)
 startActivity(it);
```
