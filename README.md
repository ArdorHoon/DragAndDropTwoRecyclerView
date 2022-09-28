# Drag and Drop between two RecyclerView 

> If this project is useful, please give it a star ⭐

An example of dragging and dropping from one `RecyclerView` into another `RecyclerView`. It can be used easily by implementing `DragListener.kt` (This code operates on android `api 24` and above.)

<img width="30%" src="https://user-images.githubusercontent.com/35184909/190032101-08e4a785-3e3b-4726-8c9f-3d54c8a3aa63.gif"/>

## 🗝 Used Libraries
* DataBinding
* ViewModel
* LifeCycles
<br>

## 🎡 Description

🔴 RecyclerViewDragAdapter : abstract class for apply drag and drop to some adpater
- isSwappable : `boolean` for swap item in same Recyclerview
- onAdapterListener : It is callback listener for handling `viewmodel`, naming is free


### 👉 Usage

```kotlin
class SimpleAdapter(
    override val isSwappable: Boolean,
    private val onAdapterListener: OnAdapterListener
) : RecyclerViewDragAdapter<SimpleModel, RecyclerView.ViewHolder>(diffUtil) {
    
    /*.skip..*/
   
    override fun onAdd(item: SimpleModel) {
       onAdapterListener.onAdd(item)
    }

    override fun onRemove(item: SimpleModel) {
       onAdapterListener.onRemove(item)
    }


    override fun onSwap(from: Int, to: Int) {
       if(currentList.any { it.isRed }){
           onAdapterListener.onSwap(true, from, to)
       }else{
           onAdapterListener.onSwap(false, from, to)
       }
    }

    override fun onSet(
        targetIndex: Int,
        sourceIndex: Int,
        targetItem: SimpleModel,
    ) {
        onAdapterListener.onSet(targetIndex, sourceIndex, targetItem)
    }
}
```
<br>

## ☘️License
```xml
Copyright 2022 Hunmo Yang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
