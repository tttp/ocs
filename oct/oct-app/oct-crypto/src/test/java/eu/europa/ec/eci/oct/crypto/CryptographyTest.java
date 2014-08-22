package eu.europa.ec.eci.oct.crypto;

import junit.framework.Assert;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptographyTest {

	/*static final String PUBK = "30820122300D06092A864886F70D01010105000382010F003082010A0282010100A33C5AC0EEDB821C54F9E7175C1A9A98395A030250141C0A450A2C200DAA429533473BFE45CC2175D2C8BFE9256AE71E317052F7072BED161FDA127B3B7E57CE095727771F545E4F7C2FF5EF36588EE9E172FD5479B9F1BDC1D49823EF4FCB6F879BB8671C53B9AEDDEDFB086D299CC2BB7CA89A8369D3D8AA0BE3EF7F5AD144628B72B5CA45AD9634D27DACD4943FD070DDC99C0C4684CC925CBBCC171336635B91FC6996241714B3AF2029AA7EBEF1422AD73B324BAD4C1AC377DE9351842C88988E01601E01670012544B1559AD35D3F121F68DCE7A2D6E4BC13D784B432718241B8584A841EE0ABC7B5BE6F00AF0D62AD2D9F92A666E5FE91B57765003D70203010001";
	static final String PRIVK = "308204BE020100300D06092A864886F70D0101010500048204A8308204A40201000282010100A33C5AC0EEDB821C54F9E7175C1A9A98395A030250141C0A450A2C200DAA429533473BFE45CC2175D2C8BFE9256AE71E317052F7072BED161FDA127B3B7E57CE095727771F545E4F7C2FF5EF36588EE9E172FD5479B9F1BDC1D49823EF4FCB6F879BB8671C53B9AEDDEDFB086D299CC2BB7CA89A8369D3D8AA0BE3EF7F5AD144628B72B5CA45AD9634D27DACD4943FD070DDC99C0C4684CC925CBBCC171336635B91FC6996241714B3AF2029AA7EBEF1422AD73B324BAD4C1AC377DE9351842C88988E01601E01670012544B1559AD35D3F121F68DCE7A2D6E4BC13D784B432718241B8584A841EE0ABC7B5BE6F00AF0D62AD2D9F92A666E5FE91B57765003D702030100010282010021C8D00FB6600021D50493EBA5C86BCEADB00F875F038A7A25A7E1521101F81DE12B1123A35688665665EE3256E835456E7E0CEDCBB22FB3B08BF5DF4384F6AF520793C777D8825C791B840F2A7451BFDE456A611D807AA40785F4A71B54AC8A3CCAE1F3954D4ED4FE61CEDF0398E534F890E864726668CE2303AAFD5AA2754518DA58110F8C0296E89D4A4B7C19C5C1BF13C97D1F0BFB164186D50102802C75B055E54FDF3282EE2529EC7B86308E7A725C23ABB63B045B41403B042CC375ECEB298043678003AD4840AAAC37B6DB0D183E77357058BA61A907062A4ECCFCFBFC54EE991E7507D89CACC051D3A2260074128D2030BDAC94176E34A822164B8102818100E6131A67AD02D6653F217FE9C0BC409F582537B48546233888DE09F5D480BDB869C23661F5AEE46944660D5B7377FEA4D08141F94262E20AF8E7765D4ADC2331C0B90CC789B5D95F3BE3A23D2BCC61F14309C67F4F06F80D2CE9AEF84F0DD0BF72E3FD764ADBA958F07D502CF1A15054E6B11E9E29D446FB948DDBA3D11EE48702818100B5A12B7913E12FC41CCB21FC07BDB09C958F1CF276BFDE7A82306F67BF0A12CA8FC0B81B04E530D86D65944015BB65458EE7C071630FC687EA4E3922EB74F35AF084C59B3AAC7E724A25BB661EA444796CD36FED2CD8AB604912102195D04964AD28F6024BE08EF6B4D5280A1E39C94805C3D105449E6BD0A217E815A8FF0A31028181009C51FE6ED2AE4ADD1FB7B3212F42B8E58B0E3E7AF8D25EBBDAC28221F4F043F738642C6F026E81E2C40652AD0017577CC3EE0ED53CB5D2BF81BE423DC0CF315A6C874E97848979D7AAE94F2220D00CFBCF80A4CD7AF45135227EE3D2F26380A1937E34885AF4446B997A8B2EFE3D8C472336D486C204E8F5A6497C2221C3CBA102818100ABE35946FE9F501B22BCC2EEC74CA785A8AC85D298BB400B6485FE088AED379767B0150BCCF831DFC0B82F32EEDE281B4A00DD48F15D0E9FB6084D5E54C29B43E6F7602365C5B4ECE86B090CBDD6EBCE96450B59C3E5515A900C7E23AAAA31AB0B8D06346A269C65B20140214335A0DD7E4730DAF17393C9207DCB70243D29210281800B3058706EC275F8EDCD8F2ACA5D786B0CBFA813B0AA63A569A72855027BABD644CBE78AF35684D4C1C8B5E8FD9A4512CC16F5E39E3B0D01AD32FA81E8E95FD8FEAD9C41BE0F2229D77B33C9E2C8481E6DB44AC97C38822E64E8BE16B87C0B0E0A0CBD2D0611CD85C7A9A25A8481D074E9354F025BE7A7E19AB4352CF4678509";
*/
	
	static byte[] privk = null;
	
	static byte[] pubk = null;
	
	@BeforeClass
	public static void setUp() {
		try {
			KeyPair kp = Cryptography.generateKeyPair();			
			privk = kp.getPrivate().getEncoded();
			pubk = kp.getPublic().getEncoded();
			Assert.assertNotNull("Generated key pair is null", kp);
			
		} catch (CryptoException e) {
			e.printStackTrace();
			Assert.fail("crypto exception: " + e.getMessage());
		} 	
		
		/*try {
			
			privk = Hex.decodeHex(PRIVK.toCharArray());
			pubk = Hex.decodeHex(PUBK.toCharArray());
		} catch (DecoderException e) {
			e.printStackTrace();
			Assert.fail("decoder exception: " + e.getMessage());
		} */	
		
	}

	
	@Test
	public void testFingerprint(){
		
		String text = "Very secret text to hash it";
		
		try {
			byte[] textHash = Cryptography.fingerprint(text.getBytes());
			byte[] hashToCompare = MessageDigest.getInstance(CryptoConstants.DIGESTER_ALG).digest(text.getBytes());
			
			Assert.assertTrue("Hashed text does not match expected", java.util.Arrays.equals(textHash, hashToCompare));
		} catch (CryptoException e) {
			e.printStackTrace();
			Assert.fail("crypto exception: " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			Assert.fail("no such algorithm: " + e.getMessage());
		}		
	}
	
	@Test
	public void testKeyPairGeneration(){		
		
		try {
			KeyPair kp = Cryptography.generateKeyPair();						
			Assert.assertNotNull("Generated key pair is null", kp);
		} catch (CryptoException e) {
			e.printStackTrace();
			Assert.fail("crypto exception: " + e.getMessage());
		} 		
	}
	
	
	@Test
	public void testEndToEnd() {

		String plaintext = "Very secret text to be encrypted";
		byte[] plainbytes = null;
		try {
			plainbytes = plaintext.getBytes("UTF8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			Assert.fail("unsupported encoding exception. message: " + e1.getMessage());
		}

		byte[] encrypted = null;
		byte[] decrypted = null;
		
		try {
			
			encrypted = new Cryptography(CipherOperation.ENCRYPT, pubk).perform(plainbytes);			
			decrypted = new Cryptography(CipherOperation.DECRYPT, privk).perform(encrypted);
		} catch (CryptoException e) {
			
			e.printStackTrace();
			Assert.fail("crypto exception: " + e.getMessage());
		}
		
		Assert.assertTrue("Decrypted text does not match expected", java.util.Arrays.equals(plainbytes, decrypted));
	}
	
	
	@Test
	public void testCompleteEndToEnd() {

		String plaintext = "łóżźćąęâăîșțâ.,";
		byte[] plainbytes = null;
		try {
			plainbytes = plaintext.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			Assert.fail("unsupported encoding exception. message: " + e1.getMessage());
		}

		byte[] encrypted = null;
		byte[] decrypted = null;
		
		try {
			
			encrypted = new Cryptography(CipherOperation.ENCRYPT, pubk).perform(plainbytes);		
			String encryptedValue = new String(Hex.encodeHex(encrypted));
			
			decrypted = new Cryptography(CipherOperation.DECRYPT, privk).perform(Hex.decodeHex(encryptedValue.toCharArray()));
		} catch (CryptoException e) {
			
			e.printStackTrace();
			Assert.fail("crypto exception: " + e.getMessage());
		}  catch (DecoderException e) {
			e.printStackTrace();
			Assert.fail("decoder exception: " + e.getMessage());
		}
		
			Assert.assertTrue("Decrypted text does not match expected", 
					java.util.Arrays.equals(plainbytes, decrypted));
		
	}
}
