# bowl-game
Bowl game results printer CLI app (Java exercise)

# build
Requires **java 11** or higher.

To install distribution locally into `./build/install` dir:
```shell
./gradlew install
```
To build zip and/or tar distribution(s) in `./build/distributions` dir:
```shell
# zip dist
./gradlew distZip

# tar dist
./gradlew distTar

# both distributions
./gradlew assembleDist
```

# test
To run all tests:
```shell
./gradlew check
```
To run unit or integration tests only:
```shell
# unit tests
./gradlew test

# integration tests
./gradlew integrationTest
```

# run
To run application from local installation:
```shell
cd build/install/bowl-game
./bin/bowl-game
```
Similarly, application can be launched from unzipped/untared distribution:
```shell
cd <unpacked_app_dir>/bowl-game-<VERSION>
./bin/bowl-game
```

# cli args
To see all available cli args:
```shell
./bowl-game --help
```
If no any arguments and options are provided then input is taken from 
STDIN and output is printed to STDOUT.

To read input from file and print output to STDOUT:
```shell
./bowl-game path-to-your-game-file.txt
```
