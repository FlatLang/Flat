<template lang="flat-html">
  <div anchor-button id="when">
    <h1>WHEN DO YOU NEED TO WRITE TARGET SPECIFIC CODE?</h1>
    <p>
      If you are writing a program that you need to be available on a wide range of devices, then
      you will most likely come across a situation where you need to write some device-specific
      code. That is where target-specific code comes into play.
    </p>
    <p>
      For example, say you are writing a mobile application. You want this app to be available on
      iPhone and Android. However, iPhone and Android apps are programmed with two different
      languages and APIs. To overcome this, you can either use a library that acts as an abstraction
      layer (more on this soon<!-- <FootnoteRef id="abstraction-layer"></FootnoteRef>-->), or you
      can write the target-specific code yourself. The first option offers a much cleaner and easier
      approach, while the second option can offer more fine tuned control on every aspect of your
      app.
    </p>
    <p>I am going to demonstrate how to write target-specific code yourself in this post.</p>
  </div>

  <div anchor-button id="organizing-code">
    <h2>ORGANIZING THE CODE</h2>
    <p>Building off of the mobile app plotline, lets lay out a package structure for this app:</p>
    <pre>
      <code class="language-bash" style="margin: 40px 0;">
        MyApp/
        |----> com/
            |----> myapp/
                |----> ui/
                    |----> MessageBox.flat
                    |----> MessageBox.swift.flat
                    |----> MessageBox.java.flat
                    |----> Table.flat
                    |----> Table.swift.flat
                    |----> Table.java.flat
                    |----> Photo.flat
                    |----> Photo.swift.flat
                    |----> Photo.java.flat
                |----> notifications/
                    |----> Notification.flat
                    |----> Notification.swift.flat
                    |----> Notification.java.flat
                    |----> PushNotification.flat
                    |----> PushNotification.swift.flat
                    |----> PushNotification.java.flat
                |----> audio/
                    |----> Sound.flat
                    |----> Sound.swift.flat
                    |----> Sound.java.flat
                    |----> Song.flat
                    |----> Song.swift.flat
                    |----> Song.java.flat
                |----> ClassA.flat
                |----> ClassB.flat
                |----> Main.flat
      </code>
    </pre>
    <p>
      Notice how there are some files with .swift and .java extensions paired with the original
      .flat files. Whenever a <i>Filename.flat</i> file has a <i>Filename.target.flat</i> file
      paired with it in the same package, the contents of the <i>Filename.target.flat</i> file
      overrides the contents of the <i>Filename.flat</i> file. Lets see an example of this:
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          // MessageBox.flat
          class MessageBox {
            visible String message

            public construct(visible String message) {}

            public show() {
              throw Exception("A target implementation needs to override this function!")
            }

            public close() {
              throw Exception("A target implementation needs to override this function!")
            }
          }
        `}
      </code>
    </pre>
    <p>
      This is our MessageBox base implementation. It expects one of the paired files to override the
      show and close functions. This is done like the following:
    </p>
    <pre>
      <code class="language-flat" style="margin: 40px 0;">
        {`
          // MessageBox.swift.flat
          class MessageBox {
            public show() {
              // do some swift message box showing code here
              external swift {
                let alert = UIAlertController(
                  title: "Alert",
                  message: #{message},
                  preferredStyle: UIAlertControllerStyle.Alert
                )

                self.present(alert, animated: true, completion: nil)
              }
            }

            public close() {
                // do some swift message box closing code here
            }
          }
        `}
      </code>
    </pre>
    <p>
      When creating a target-specific file override, you only need to add the functions that you are
      overriding (e.g. no need to override the constructor). It is in the target-specific functions
      above that you will call the corresponding swift code necessary to <i>show</i> and
      <i>close</i> the MessageBox.
    </p>
    <p>
      You only need to add these target-specific file overrides for files that have different
      implementations depending on the language the program is being compiled to.
    </p>
  </div>

  <div anchor-button id="compiling-code">
    <h2>COMPILING THE CODE</h2>
    <p>
      When you want to compile your code to the specific target (Swift or Java in this case), you
      need to tell the compiler which target to output as. Assuming you have the required
      compilation targets included in your Flat compiler package, you can use the following
      commands:
    </p>
    <code class="language-bash" style="margin: 40px 0;">
      flatc MyApp -target swift -d MySwiftApp
    </code>
    <p>and</p>
    <code class="language-bash" style="margin: 40px 0;">
      flatc MyApp -target java -d MyJavaApp
    </code>
    <p>
      will do the trick. After running these commands, you will have a MySwiftApp and MyJavaApp
      directory created that contains the swift and java code respectively available to use for your
      iOS and Android app!
    </p>
  </div>

  <div anchor-button id="best-of-both-worlds">
    <h2>THE BEST OF BOTH WORLDS</h2>
    <p>
      The ability to separate target-specific code into separate files in Flat offers a clean
      solution to managing target-specific code across a project where you might have otherwise had
      to write two completely separate codebases for each individual target. With the integration
      offered by the target-specific files, you retain access to all of the general libraries and
      functionality written for your app across each of the different targets, while allowing
      complete control over each of the target specific aspects. You get the best of both worlds.
    </p>
    <p>
      It makes sense to write the code for a single app in a single codebase, regardless of the
      device that it runs on. Target-specific files is Flat's answer to the small percentage of
      target-specific code needed between each target.
    </p>
  </div>
</template>
