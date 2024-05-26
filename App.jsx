import axios from 'axios';
import { StyleSheet, TextInput, View, Button, Alert, Text, NativeModules } from 'react-native';


export default function App() {

  const { HyperPay } = NativeModules;


  const handleStatus =(resourcePath)=>{
    axios(`http://10.0.2.2/demo_sdk/status.php?resourcePath=${resourcePath}`)
    .then(response=>{
      console.log(response.data)
      const {result} = response.data
      Alert.alert(`Transaction code: ${result.code}\n description : ${result.description}`)
    })
    .catch(error=>{
      console.log(error)
      Alert.alert("get status faild ! , check the console")
    })
  }

  const prepareCheckout = async () => {
    try {
      response = await axios.get("http://10.0.2.2/demo_sdk")
      return response.data
    } catch (error) {
      console.log(error)
    }

  }


  const handleCheckout = async () => {

    checkoutId = await prepareCheckout()

    try {
      const resourcePath = await HyperPay.startCheckout(checkoutId)
      console.log(resourcePath)
      handleStatus(resourcePath)

    } catch (error) {
      console.log(error)
    }
  }


  return (
    <View style={styles.container}>

      <Text>Ready UI Checkout</Text>
      <Button onPress={handleCheckout} title='Checkout' />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 10,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },

  input1: {
    flex: 1,
    backgroundColor: '#fff',
    borderBlockColor: 'pink',
  },
  img: {
    width: 100,
    height: 100,

  }
});
