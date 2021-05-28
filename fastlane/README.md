fastlane documentation
================
# Installation in ubuntu 20.04

Make sure you have the latest version of the ruby, command line tools installed to check version:

```
ruby -v
```

If not installed. Then install it run command

```
sudo apt install ruby-full
```
## Make sure to run below all comments in you project directory terminal

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew install fastlane`

Now, run

```
fastlane init
```

1. Provide package name: com.abc.abc
2. Path to json secret file(optional)(if you kept json file in project root dir then provide service_account.json else simply press enter)
3. Do you plan on uploading app to google playstore?(optional) simply type "_n_"

# Available Actions
## Android
### android test
```
fastlane android test
```
Runs all the tests
### android beta
```
fastlane android beta
```
Submit a new Beta Build to Crashlytics Beta
### android deploy
```
fastlane android deploy
```
Deploy a new version to the Google Play
### android distribute
```
fastlane android distribute
```
Lane for distributing app using Firebase App Distributions

```
desc "Lane for distributing app using Firebase App Distributions"
  lane :distribute do
      gradle(task: "clean assembleQa")
      firebase_app_distribution(
          service_credentials_file: "service_account.json",
          app: "1:1971417930:android:2e82c10471c34aac3",
          release_notes: "Note: test",
          groups: "AppDistributionTesterGroup",
          firebase_cli_token: "1//0gd29wq-7vyTcCgAGBASNwF-L9IrAVnuv-G34MEErW92aBky9_J-G5lK9Kqh9_-K0Kx2vZfaV4-w73fyj_epgTTs"
       )
   end
```

Install firebase_app_distribution plugin

```
sudo fastlane add_plugin firebase_app_distribution
```

finally, run this command to execute

```
fastlane distribute
```

Reference:
    https://firebase.google.com/docs/app-distribution/android/distribute-fastlane
    https://androidbash.medium.com/automating-publishing-android-app-on-firebase-and-play-store-3aa2c746aeb4
    https://proandroiddev.com/automating-the-android-build-and-release-process-using-fastlane-part-i-fb3ce61b678

----
