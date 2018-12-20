package utils;

public final class Encryption {

  public static String encryptDecryptXOR(String rawString) {

    // If encryption is enabled in Config.
    if (Config.getEncryption()) {

      // The key is predefined and hidden in code
      // TODO: Create a more complex code and store it somewhere better :FIXED
      //det er her vi lavet vores encryptionkey hvor den gemmes i condig.json filen.
      char[] key = Config.getEncrytionKey();

      // Stringbuilder enables you to play around with strings and make useful stuff
      StringBuilder thisIsEncrypted = new StringBuilder();

      // binary tabel er XOR. forklaret i rapporten.
      // TODO: This is where the magic of XOR is happening. Are you able to explain what is going on? :fixed
      for (int i = 0; i < rawString.length(); i++) {
        thisIsEncrypted.append((char) (rawString.charAt(i) ^ key[i % key.length]));
      }

      // We return the encrypted string
      return thisIsEncrypted.toString();

    } else {
      // We return without having done anything
      return rawString;
    }
  }
}
