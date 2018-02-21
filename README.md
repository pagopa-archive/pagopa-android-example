# Running the example

The application is completely self-contained. You should just be able to import it into Android Studio and everything should work out of the box.

[![Join the #pagopa channel](https://img.shields.io/badge/Slack%20channel-%23pagopa-blue.svg?logo=slack)](https://developersitalia.slack.com/messages/C8HC6FVE0)
[![Get invited](https://slack.developers.italia.it/badge.svg)](https://slack.developers.italia.it/)
[![PagoPA on forum.italia.it](https://img.shields.io/badge/Forum-PagoPA-blue.svg)](https://forum.italia.it/c/pagopa)


## Using the SDK

While we are working to open up the source code of the SDK, you can already use its binary version.

The SDK is used through a singleton.

To initialize the Singleton instance of the SDK, invoke the following method when the program starts:

```.java
PagoPaCore.init();
```

To initiate a payment, you must use the following method:

```.java
PagoPaCore.getInstance().startPayment(@NonNull Activity activityHost, @NonNull String idPayment, PpaSessionCallback callback);
```

- `activityHost` represents the activity from which the SDK screens will be shown
- `idPayment` represents the payment id
- `callback` is an interface that lets you know if the request was successful. In case of success, it returns the `PpaPaymentVm` object, containing the payment information.

```.java
// A random payment ID, just to serve as an example.
//It needs to be replaced by the real one
String idPayment = UUID.randomUUID().toString();

PagoPaCore.getInstance().startPayment(this, idPayment, new

PpaSessionCallback() {
  @Override
  public void onPaymentCompleted(PpaPaymentVm payment) {
    // Here the payment has been completed.
    // The payment object contains the following data:

    // Amount
    String amount = payment.getAmount();

    // E-mail, associated to the payment
    String email = payment.getEmail();

    // Name of the person paying
    String name = payment.getName();

    // Name of the payment receiver
    String receiver = payment.getReceiver();

    // Reason/Subject of the payment
    String subject = payment.getSubject();
  }

  @Override
  public void onPaymentFailed() {
    // If we get here, payment has failed.
  }

  @Override
  public void onPaymentAbortedByUser() {
    // Payment has been aborted by user.
  }

});

```

# Licensing

Licensed under the BSD-3-Clause license agreement. See [`LICENSE`](LICENSE) for further details.
The `WalletCoreSDK` library, interacting with the core data of a card, is not part of this repository and is not licensed under BSD-3-Clause, and it is only distributed under binary form, in order to be compliant with the PCI certification. You can use and redistribute the binary component as you wish, but with no warranty whatsoever. Please see [its license](WalletCoreSDK/LICENSE.md) (in Italian) for further details and the full text of the license.
