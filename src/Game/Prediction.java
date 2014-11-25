package Game;

public class Prediction {

}

////=============================================================================
////An Entity in the world.
////=============================================================================
//var Entity = function() {
//this.x = 0;
//this.speed = 2; // units/s
//}
//
////Apply user's input to this entity.
//Entity.prototype.applyInput = function(input) {
//this.x += input.press_time*this.speed;
//}
//
//
////=============================================================================
////A message queue with simulated network lag.
////=============================================================================
//var LagNetwork = function() {
//this.messages = [];
//}
//
////"Send" a message. Store each message with the timestamp when it should be
////received, to simulate lag.
//LagNetwork.prototype.send = function(lag_ms, message) {
//this.messages.push({recv_ts: +new Date() + lag_ms,
//                payload: message});
//}
//
////Returns a "received" message, or undefined if there are no messages available
////yet.
//LagNetwork.prototype.receive = function() {
//var now = +new Date();
//for (var i = 0; i < this.messages.length; i++) {
//var message = this.messages[i];
//if (message.recv_ts <= now) {
//this.messages.splice(i, 1);
//return message.payload;
//}
//}
//}
//
//
////=============================================================================
////The Client.
////=============================================================================
//var Client = function() {
//this.entity = null;
//
//// Input state.
//this.key_left = false;
//this.key_right = false;
//
//// Simulated network connection.
//this.network = new LagNetwork();
//this.server = null;
//
//// Unique ID of our entity. Assigned by Server on connection.
//this.entity_id = null;
//
//// Data needed for reconciliation.
//this.input_sequence_number = 0;
//this.pending_inputs = [];
//}
//
//
////Update Client state.
//Client.prototype.update = function() {
//// Listen to the server.
//this.processServerMessages();
//
//if (this.entity == null) {
//return;  // Not connected yet.
//}
//
//// Process inputs.
//this.processInputs();
//
//// Render the World.
//renderWorld(player_canvas, [this.entity]);
//
//// Show some info.
//var info = "Non-acknowledged inputs: " + this.pending_inputs.length;
//player_status.textContent = info;
//}
//
//
////Get inputs and send them to the server.
////If enabled, do client-side prediction.
//Client.prototype.processInputs = function() {
//// Compute delta time since last update.
//var now_ts = +new Date();
//var last_ts = this.last_ts || now_ts;
//var dt_sec = (now_ts - last_ts) / 1000.0;
//this.last_ts = now_ts;
//
//// Package player's input.
//var input;
//if (this.key_right) {
//input = { press_time: dt_sec };
//} else if (this.key_left) {
//input = { press_time: -dt_sec };
//} else {
//// Nothing interesting happened.
//return;
//}
//
//// Send the input to the server.
//input.input_sequence_number = this.input_sequence_number++;
//input.entity_id = this.entity_id;
//this.server.network.send(client_server_lag, input);
//
//// Do client-side prediction.
//if (client_side_prediction) {
//this.entity.applyInput(input);
//}
//
//// Save this input for later reconciliation.
//this.pending_inputs.push(input);
//}
//
//
////Process all messages from the server, i.e. world updates.
////If enabled, do server reconciliation.
//Client.prototype.processServerMessages = function() {
//while (true) {
//var message = this.network.receive();
//if (!message) {
//break;
//}
//
//// World state is a list of entity states.
//for (var i = 0; i < message.length; i++) {
//var state = message[i];
//
//if (state.entity_id == this.entity_id) {
//  // Got the position of this client's entity.
//
//  if (!this.entity) {
//    // If this is the first server update, create a local entity.
//    this.entity = new Entity();
//  }
//
//  // Set the position sent by the server.
//  this.entity.x = state.position;
//
//  if (server_reconciliation) {
//    // Server Reconciliation. Re-apply all the inputs not yet processed by
//    // the server.
//    var j = 0;
//    while (j < this.pending_inputs.length) {
//      var input = this.pending_inputs[j];
//      if (input.input_sequence_number <= state.last_processed_input) {
//        // Already processed. Its effect is already taken into account
//        // into the world update we just got, so we can drop it.
//        this.pending_inputs.splice(j, 1);
//      } else {
//        // Not processed by the server yet. Re-apply it.
//        this.entity.applyInput(input);
//        j++;
//      }
//    }
//  } else {
//    // Reconciliation is disabled, so drop all the saved inputs.
//    this.pending_inputs = [];
//  }
//} else {
//  // TO DO: add support for rendering other entities.
//}
//}
//}
//}
//
//
////Set up the Client update loop.
//var updateClient = function() {
//client.update();
//}
//setInterval(updateClient, 1000 / client_fps);
//
//
////=============================================================================
////The Server.
////=============================================================================
//var Server = function() {
//// Connected clients and their entities.
//this.clients = [];
//this.entities = [];
//
//// Last processed input for each client.
//this.last_processed_input = [];
//
//// Simulated network connection.
//this.network = new LagNetwork();
//}
//
//Server.prototype.connect = function(client) {
//// Give the Client enough data to identify itself.
//client.server = this;
//client.entity_id = this.clients.length;
//this.clients.push(client);
//
//// Create a new Entity for this Client.
//var entity = new Entity();
//this.entities.push(entity);
//entity.entity_id = client.entity_id;
//
//// Set the initial state of the Entity (e.g. spawn point)
//entity.x = 5;
//}
//
//Server.prototype.update = function() {
//this.processInputs();
//this.sendWorldState();
//renderWorld(server_canvas, this.entities);
//}
//
//
////Check whether this input seems to be valid (e.g. "make sense" according
////to the physical rules of the World)
//Server.prototype.validateInput = function(input) {
//if (Math.abs(input.press_time) > 1/40) {
//return false;
//}
//return true;
//}
//
//
//Server.prototype.processInputs = function() {
//// Process all pending messages from clients.
//while (true) {
//var message = this.network.receive();
//if (!message) {
//break;
//}
//
//// Update the state of the entity, based on its input.
//// We just ignore inputs that don't look valid; this is what prevents
//// clients from cheating.
//if (this.validateInput(message)) {
//var id = message.entity_id;
//this.entities[id].applyInput(message);
//this.last_processed_input[id] = message.input_sequence_number;
//}
//}
//}
//
//
////Send the world state to all the connected clients.
//Server.prototype.sendWorldState = function() {
//// Gather the state of the world. In a real app, state could be filtered to
//// avoid leaking data (e.g. position of invisible enemies).
//var world_state = [];
//var num_clients = this.clients.length;
//for (var i = 0; i < num_clients; i++) {
//var entity = this.entities[i];
//world_state.push({entity_id: entity.entity_id,
//                position: entity.x,
//                last_processed_input: this.last_processed_input[i]});
//}
//
//// Broadcast the state to all the clients.
//for (var i = 0; i < num_clients; i++) {
//var client = this.clients[i];
//client.network.send(client_server_lag, world_state);
//}
//}
//
//
//
//
////Set up the Server update loop.
//var updateServer = function() {
//server.update();
//}
//var server_interval = setInterval(updateServer, 1000 / server_fps);
//
//
////Connect the Client to the Server.
//server.connect(client);
//
//}