
const {onRequest} = require("firebase-functions/v2/https");
const admin = require("firebase-admin");

// eslint-disable-next-line max-len
const serviceAccount = require("./habitat-bf6d3-firebase-adminsdk-x3te3-0a4733231f");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

exports.createCustomToken = onRequest(async (request, response) => {
  const user = request.body;

  const uid = `kakao:${user.uid}`;
  const updateParams = {
    email: user.email,
    photoURL: user.photoURL,
    displayName: user.displayName,
  };

  try {
    await admin.auth().updateUser(uid, updateParams);
  } catch (e) {
    updateParams["uid"] = uid;
    await admin.auth().createUser(uid, updateParams);
  }

  const token = await admin.auth().createUser(user);

  response.send(token);
});
