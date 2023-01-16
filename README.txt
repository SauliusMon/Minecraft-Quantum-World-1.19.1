# Quantum-World

# PokerGame-Task


Newest edition of this mod can be found on newest-version branch.

Hi there,

About the mod itself:
   This is a tech-based mod focused on electricity and quantum physics. End-game goal would be to make a multi-block particle accelerator, which would be able to produce antimatter by smashing two particles and consuming a massive amount of energy. You would achieve that by collecting a variety of different materials required for crafting, defeating different type of mobs and bosses while traveling to different dimension(s) and building an energy system capable of providing energy to previously mentioned particle accelerator.
   
Main goals:
   Make the mod gameplay enjoyable, bugless and efficient in terms of performance. Current mod iterations are focused on putting more strain on server side, while preserving client side for textures and other visuals loading.
   
Code logic:
   Usage of multiple base classes, which are extended by that particular type of block (e.g. CableBaseClass being extended by FiberOpticCableBlock). Static methods in tools directory are used for universal functions, such as finding nearby electric entities or converting enum to direction without creating an object. In theory this should diminish usage of Java garbage collectors and improve mod performance. In later versions, static methods might become a part of the new abstract class BaseEnergy.
   Textures (.png files) are created using Gimp.
   
Main problems:
   Slow electricity flow between cables at a large distances (need to develop a new system or implement path-finding algorithm).
   
Version (state):
   This mod is still in an EARLY DEVELOPMENT STAGE.
   Will be used in 1.19.2 Forge version (probably).
   
Contacts:
   If you have any suggestions or (and) found a bug in a code while playing the mod, contact me through github or email: ale.vidmantas@gmail.com
   
Creators:
  Currently, all textures and code was written by me.
   
   
Special thanks to:
Kaupenjoe - for awesome youtube tutorials about minecraft modding,
Forge forums modder support community - for quick and helpful answers regarding modding.
