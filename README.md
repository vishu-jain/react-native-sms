# react-native-sms-fetch

React Native package to retreive sms from Android only

## Installation

```sh
npm install react-native-sms-fetch
```

## Android manual Installation

**NOTE: getSms() method only works with these changes:**

In your `android/app/src/main/AndroidManifest.xml`

```xml
<uses-permission android:name="android.permission.READ_SMS" />
```

## Usage


```js
import { getSms } from 'react-native-sms-fetch';

// ...

const result = await getSms();
```


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
