CapAPI Android Client:

Core Functionality: Mirroring the iOS app, the Android version allows users to view, save, and manage their favorite cat images.

Architecture: Also follows the MVVM pattern, leveraging Kotlin's language features for concise and expressive code.

Network Layer: Retrofit and Moshi were used for network communication and JSON serialization/deserialization, providing a robust and efficient way to interact with The Cat API.

User Interface: Implemented using Jetpack Compose, offering a declarative approach to building UIs with less boilerplate than traditional XML layouts.

State Management: Utilized LiveData and StateFlow for reactive UI updates in response to data changes.

Testing: Emphasized testing with JUnit and MockK for ViewModel and network layer testing, ensuring the app's stability and functionality.


Improvement Areas: UI Tests, offline capabilities, caching, accesibility, app icons

Challenges for the short time period: 
- Interoperability between Fragments and Jetpack Compose
- There's some nice things like custom decoders, Mockable network layer, reusable compose components