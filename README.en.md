#StregnthPlus

## introduce
A simple and lightweight java enhancement plug-in, which strengthens items by adding or removing lore to items, and makes damage judgments when players attack.
If there is an enhancement level, the corresponding damage will be added, otherwise, the corresponding damage reduction will be performed. to achieve the effect of strengthening the item. If the item is over level 8, then if
Strengthening is not strengthening for protection, then the item will shatter and disappear when the strengthening fails (this is irreversible, please pay attention!). Allowed for each level of reinforcement
Configure the damage bonuses and reductions you want. If the defense overflows, it is allowed to configure the minimum damage value to ensure that it is not completely harmless.

Notice! Please balance the damage and defense values by yourself. If there is an imbalance problem, it will not be dealt with. The default values of this plugin are for reference only.

### Software Architecture
#### 1. Local file
    1. ConfigFactory
    The plugin uses the FileConfiguration class in native Spigot and customizes a configFactory for local configuration.
    Configuration file reading By default, the configuration file basically does not have errors, if it exists, please delete the configuration file according to the forum
    Configure the default configuration file in or delete your configuration file using the /sp reload directive to reload the configuration file.
    Four Bean objects are defined in the configFactory, and the parameters to be read are encapsulated into its beans.
    Use the StrengthExtra class for integration.
#### 2. Strengthen the module

    1. Dao layer:
        1.strengthDao
        In strengthDao, the strengthening method of the item is realized, and the lore increase of the item and the basic strengthening and strengthening function are carried out.
        Realize the broadcast function of strengthening announcement. Also realized the giving function of strengthening stone items.
    Second, the service layer:
        1.strengthService
        Complex logical judgments are implemented in strengthService, dao layer methods are executed, and various judgments and logical processing are performed.
        It will be determined whether it is feasible to strengthen, fill the player's backpack with the strengthening stone, strengthen the retrieval of items and other functions, and finally hand it over to the
        commandHander to handle the call.
    Three, CommandHander layer:
        Here, a full range of command integration is performed, various methods in the service are executed, and some simple problems are dealt with. will
        The result is passed back to the player, and the result is returned.
#### 3. Damage Monitor
    The plugin will obtain the damage parameters for monitoring, and add the item level obtained by the item to obtain the damage parameters.
    If the damage value obtained by the level is lower than the defense value, the damage will be added according to the minimum damage, and vice versa.
#### 4. Default utils
    In plug-in development, I encapsulated some default development Utils to help with secondary development, or make the modifications I want
    to make it easier for individuals to make changes. These are some simple gadgets, I hope they can help you.
### Installation Tutorial
1. Move the plugin to the plugin folder of the server
2. Start/restart the server
3. Use

### Instructions for use
#### 1. Plugin permissions
    strength.admin (for using various admin commands)
#### 2. Plugin directives
    /sp is used to display enhanced menu information
    /sp normal for normal item enhancement
    /sp safe for protection enhancement, the protected and enhanced items will not be broken because they exceed level 8, and protection enhancement stones are required
    /sp success A must-success enhancement command, requires a must-success enhancement stone
    -------------------The following commands require strength.admin privileges----------------------
    Give command: (if it exceeds, it will send the exceeding value)
    /sp normalstone amount Gives the normal enhanced stone command, the amount is the amount
    /sp safestone amount Give the command of protection enhancement stone, amount is the amount
    /sp successstone amount Give the command of sure success enhancement stone, amount is the amount
    /sp admin directly strengthen the item to level 10, note that a broadcast announcement will be sent, and the item level color is different from
    Normal Enhanced Color.
    /sp reload to reload the configuration file
#### 3. Configuration file example
    #Enhanced plugin configuration file
    # Sword damage per level (melee)
    sword: 1.5
    # Bow damage per level
    bow: 1.5
    # Crossbow damage per level
    crossbow: 1.3
    # The damage value of armor enhancement level 1 resistance
    defence: 0.5
    # When the defense damage is higher than the attack damage (the strengthening level is too high to cause scraping damage that does not drop blood)
    min_damage: 0.2
    strength_stone:
    stone_normal:
    #Configure item type
    item: SPONGE
    #Configure the name of the strengthening stone
    name: '&bEnhanced Stone'
    #Configure enhanced stone lore, allowing multiple lines of lore
    lore:
    - '&aGood stuff for strengthening weapons and armor'
    stone_safe:
    # Allow to fill in item type
    item: PAPER
    name: '&cProtect Hardened Volume'
    # Configure whether to allow protection stones for protection
    safe: true
    lore:
    - '&bUsed to protect weapons beyond &c[&a8&c]&b from being broken after being strengthened.'
    stone_success:
    item: EMERALD
    name: '&eSuccessful Enhancement Stone'
    # Configure whether to allow certain success stones to succeed
    success: true
    lore:
    - '&6Enhancing stone that is guaranteed to succeed, as long as you use it, then this enhancement will definitely succeed'
    # Enhancement probability, a total of ten levels, if it exceeds, it will not be read
    strength_chance:
    - 100
    - 90
    - 80
    - 70
    - 60
    - 50
    - 40
    - 30
    - 20
    - 10
    #Strengthen the name, the item name must be correct
    itemName:
    - 'WOODEN_SWORD'
    - 'STONE_SWORD'
    - 'IRON_SWORD'
    - 'GOLDEN_SWORD'
    - 'DIAMOND_SWORD'
    - 'NETHERITE_SWORD'
    - 'WOODEN_AXE'
    - 'STONE_AXE'
    - 'IRON_AXE'
    - 'GOLDEN_AXE'
    - 'DIAMOND_AXE'
    - 'NETHERITE_AXE'
    - 'BOW'
    - 'CROSS_BOW'
    - 'DIAMOND_HELMET'
    - 'DIAMOND_CHESTPLATE'
    - 'DIAMOND_LEGGINGS'
    - 'DIAMOND_BOOTS'
    - 'NETHERITE_HELMET'
    - 'NETHERITE_CHESTPLATE'
    - 'NETHERITE_LEGGINGS'
    - 'NETHERITE_BOOTS'
    - 'IRON_HELMET'
    - 'IRON_CHESTPLATE'
    - 'IRON_LEGGINGS'
    - 'IRON_BOOTS'
    - 'GOLDEN_HELMET'
    - 'GOLDEN_CHESTPLATE'
    - 'GOLDEN_LEGGINGS'
    - 'GOLDEN_BOOTS'

### Contribute
Raven (developed the entire project on his own and perfected the code)