package io.scalechain.blockchain.proto

import java.math.BigInteger

/** The super class for all protocol messages.
  * The interface of protocol encoder and decoder will use ProtocolMessage type.
  * (Protocol encoder and decoder serialize and deserialize the message.)
  *
  * See also : BitcoinProtocolEncoder, BitcoinProtocolDecoder.
  */
abstract class ProtocolMessage

/** protocols.scala ; Define protocol case classes that are aware of serialization and deserialization about the message.
 * Each case class itself consists of protocol fields.
 *
 * Case class names and comments are copied from https://en.bitcoin.it/wiki/Protocol_documentation .
 * The license of Bitcoin Wiki is Creative Commons Attribution 3.0.
 */

/** Version ; When a node creates an outgoing connection, it will immediately advertise its version.
  *  The remote node will respond with its version. No further communication is possible until both peers have exchanged their version.
 */
case class VersionMessage() extends ProtocolMessage

/** Verack ; The verack message is sent in reply to version.
 * This message consists of only a message header with the command string "verack".
 */
case class VerackMessage() extends ProtocolMessage

/** Addr ; Provide information on known nodes of the network.
 *  Non-advertised nodes should be forgotten after typically 3 hours
 */
case class AddrMessage() extends ProtocolMessage

/** Inv ; Allows a node to advertise its knowledge of one or more objects.
 *  It can be received unsolicited, or in reply to getblocks.
 */
case class InvMessage() extends ProtocolMessage

/** GetData ; getdata is used in response to inv, to retrieve the content of a specific object,
 * and is usually sent after receiving an inv packet, after filtering known elements.
 * It can be used to retrieve transactions, but only if they are in the memory pool or
 * relay set - arbitrary access to transactions in the chain is not allowed
 * to avoid having clients start to depend on nodes having full transaction indexes
 * (which modern nodes do not).
 */
case class GetDataMessage() extends ProtocolMessage

/** NotFound ; notfound is a response to a getdata, sent if any requested data items could not be relayed.
 * For example, because the requested transaction was not in the memory pool or relay set.
 */
case class NotFoundMessage() extends ProtocolMessage

/** GetBlocks; Return an inv packet containing the list of blocks
 * starting right after the last known hash in the block locator object,
 * up to hash_stop or 500 blocks, whichever comes first.
 * The locator hashes are processed by a node in the order as they appear in the message.
 * If a block hash is found in the node's main chain, the list of its children is returned back via the inv message and
 * the remaining locators are ignored, no matter if the requested limit was reached, or not.
 *
 * To receive the next blocks hashes, one needs to issue getblocks again with a new block locator object.
 * Keep in mind that some clients may provide blocks which are invalid
 * if the block locator object contains a hash on the invalid branch.
 */
case class GetBlocksMessage() extends ProtocolMessage

/** GetHeaders ; Return a headers packet containing the headers of blocks starting right after the last known hash
 * in the block locator object, up to hash_stop or 2000 blocks, whichever comes first.
 * To receive the next block headers, one needs to issue getheaders again with a new block locator object.
 * The getheaders command is used by thin clients to quickly download the block chain where the contents
 * of the transactions would be irrelevant (because they are not ours).
 * Keep in mind that some clients may provide headers of blocks which are invalid
 * if the block locator object contains a hash on the invalid branch.
 */
case class GetHeadersMessage() extends ProtocolMessage

/** Tx ; tx describes a bitcoin transaction, in reply to getdata.
 */
case class TxMessage() extends ProtocolMessage

/** The block message is sent in response to a getdata message
 * which requests transaction information from a block hash.
 */
case class BlockMessage() extends ProtocolMessage

/** Headers ; The headers packet returns block headers in response to a getheaders packet.
 */
case class HeadersMessage() extends ProtocolMessage

/** GetAddr ; The getaddr message sends a request to a node asking
 * for information about known active peers to help with finding potential nodes in the network.
 * The response to receiving this message is to transmit one or more addr messages with one or
 * more peers from a database of known active peers.
 * The typical presumption is that a node is likely to be active* if it has been sending a message within the last three hours.
 *
 * No additional data is transmitted with this message.
 */
case class GetAddrMessage() extends ProtocolMessage

/** Mempool ; The mempool message sends a request to a node asking for information
 * about transactions it has verified but which have not yet confirmed. The response to receiving this message is an inv message containing the transaction hashes for all the transactions in the node's mempool.
 * No additional data is transmitted with this message.
 * It is specified in BIP 35. Since BIP 37, if a bloom filter is loaded, only transactions matching the filter are replied.
 *
 */
case class MempoolMessage() extends ProtocolMessage

/** CheckOrder ; This message was used for IP Transactions. As IP transactions have been deprecated, it is no longer used.
 */
@deprecated
case class CheckOrderMessage() extends ProtocolMessage

/** SubmitOrder ; This message was used for IP Transactions. As IP transactions have been deprecated, it is no longer used.
 */
@deprecated
case class SubmitOrderMessage() extends ProtocolMessage

/** Reply ; This message was used for IP Transactions. As IP transactions have been deprecated, it is no longer used.
 */
@deprecated
case class ReplyMessage() extends ProtocolMessage

/** Ping ; The ping message is sent primarily to confirm that the TCP/IP connection is still valid.
 * An error in transmission is presumed to be a closed connection and the address is removed as a current peer.
 *
 * Field Size,  Description,  Data type,  Comments
 * ================================================
 *          8,        nonce,   uint64_t,  random nonce
 */
case class PingMessage(val nonce : Long = 0) extends ProtocolMessage

/** Pong ; The pong message is sent in response to a ping message.
 * In modern protocol versions, a pong response is generated using a nonce included in the ping.

  * Field Size,  Description,  Data type,  Comments
  * ================================================
  *          8,        nonce,   uint64_t,  random nonce

 */
case class PongMessage(val nonce : Long = 0) extends ProtocolMessage

/** Reject ; The reject message is sent when messages are rejected.
 */
case class RejectMessage() extends ProtocolMessage

/** These messages are related to Bloom filtering of connections and are defined in BIP 0037.
 * Details : https://en.bitcoin.it/wiki/BIP_0037
 */
case class FilterLoadMessage() extends ProtocolMessage

/** FilterAdd; TODO : Copy & paste description from Bitcoin protocol wiki.
 *
 */
case class FilterAddMessage() extends ProtocolMessage

/** FilterClear; TODO : Copy & paste description from Bitcoin protocol wiki.
  *
  */
case class FilterClearMessage() extends ProtocolMessage

/** FilterBlock; TODO : Copy & paste description from Bitcoin protocol wiki.
  *
  */
case class MerkleBlockMessage() extends ProtocolMessage

/** An alert is sent between nodes to send a general notification message throughout the network.
 * If the alert can be confirmed with the signature as having come from the core development group of the Bitcoin software,
 * the message is suggested to be displayed for end-users. Attempts to perform transactions,
 * particularly automated transactions through the client, are suggested to be halted.
 *
 * The text in the Message string should be relayed to log files and any user interfaces.
 */
case class AlertMessage() extends ProtocolMessage
