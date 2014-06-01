# Forager


## This is a Forager game.

Your aim is to develop an agent brain, which will be implented on multiple agents so that they'll harvest all the food on the map as fast as possible. The agent can communicate, move and reproduce as long as they have enough food stored and eat on a
regular basic.

An [example](https://github.com/elnabo/Forager/blob/master/examples/DummyBrain.java) is available, and there is some [online doc](http://elnabo.github.io/forager/api/)

## How to use.

* Compilation : ant compile
* Example execution : ant run
* Your brain execution 
    ** Windows : java -cp ".;build;build/madkit-5.jar;pathToYourBrain" forager.Main YourBrainClassName
    ** Linux : java -cp ".:build:build/madkit-5.jar:pathToYourBrain" forager.Main YourBrainClassName
