### 1.3.5:
- Fix freeze/crash when generating structures with _Armor Stands_ of certain datapacks (e.g. [Luki's Grand Capitals](https://www.modrinth.com/datapack/lukis-grand-capitals))
### 1.3.4:
- `1.21.6⁺`: Update to 1.21.6⁺
### 1.3.3:
- `1.20.1`, `1.21(.1)`: Fix crash on startup (when using certain mods e.g. *EMI* ) introduced by previous version `1.3.2`
### 1.3.2:
- `Neoforge`¹:  
  Disable `1.3.0`'s _"Armor Stands in Structures replacement"_-feature
  > **Reason**: This feature is not compatible with Neoforge's own changes to the affected code.

  > **Note**: This functionality will eventually be re-added with *[Quad](https://modrinth.com/mod/quad)* `1.3.0`'s full release for *Neoforge* or *More Armor Stand Variants*' own *Neoforge* release, whichever is earlier.

¹<sup>) Not officially supported.</sup>
### 1.3.1:
- `1.21.5`: Update to 1.21.5
## 1.3.0:
- Implement Armor Stands spawning as part of a structure matching their wood variant to the biome at their position
  - **Vanilla**: Only _Taiga Villages_ are affected, the Armor Stands of the Armorer spawn as the Spruce variant  
    ![](https://uwu.catgirl.host/i/8hz5m.png)
  - **Modded**: Any structure in a vanilla biome that has an associated wood type will spawn its Armor Stands using that biome's matching variant
### 1.2.1:
- Add Ukrainian Translation (by [Starman](https://modrinth.com/user/StarmanMine142))
## 1.2.0:
- Add Dispenser functionality for all Armor Stand variants
- `1.21.3⁺` Add _**Pale Oak** Armor Stand_ (requires `MStV 1.3.0`)
- `1.21.4`: Update to 1.21.4
### 1.1.2:
- Add breaking particles according to wood type
## 1.1.0:
- Implement 'Pick Block'-functionality returning the correct variant when middle-clicking an Armor Stand in Creative Mode
- `1.21.2`, `1.21.3`: Update to 1.21.2, 1.21.3
### 1.0.1:
- `1.20.1`, `1.20.4`: Correct Java version from 21 to 17
