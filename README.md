## Implementation Details.
- The app demonstrates a strong adherence to the MVVM Architecture, with its components thoughtfully organized into packages, ensuring modularity, scalability, and testability.
- Dagger Hilt
- Remote and Local data sources
- Coroutines with Flow
- Retrofit for remote layer and Room DB for local layer
- List adapter with DiffUtil
- Data classes for different layers with mappers
- Navigation Component/Graph

## Fetching School List and sat scores
- ListViewModel fetches the list of schools and list of SAT scores. The method to get data is called in init so that the call is not made again on device rotation.
- Repository will check for data in the local data source - room DB, if it exists repository returns data from database. If data not found in local data source it will be fetched from remote data source.

## Enhancements
- Implement policies to delete data from database based. Currently the database will keep based on api response which is not ideal, this is to limit time spent on implementation.
- UI can be further enhanced. I spend more time on the architecture, offline support implementation.
- Test cases for View Model to test emissions from State flow.
- Dimensions are hard coded in xml, need to be extracted in dimens.xml. This is to limit time spent on implementation.

## References
- Repeat on lifecycle -  https://medium.com/androiddevelopers/repeatonlifecycle-api-design-story-8670d1a7d333

## How to build/run app.
- Minimum API 24, Target SDK 33. Works in landscape and portrait mode.

