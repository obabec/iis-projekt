# iis-projekt

Repository for IIS project. Library information system.

## Assignment

Assignment for project can be found under subfolder `zadani`. Abstract is regular assignment. Knihovna is concrete
assignment with details about our system.

## Application deployment
Application can be deployed via script
```
./docker/start_docker.sh -d
```
`-d` option is for detached run, if you want deploy application otherwise checkout `-h` of the script.

## Decommissioning
```
./docker/start_docker.sh -k
```

## Project structure
### Controllers - logical 
Controllers can be found under `controllers` package
### Model classes
Classes for mapping database to software model will be stored under package `model`
### Templates
Templates can be found under `resources/templates` package

## Documentation
Write docs whenever you think it's important for others when they build in top of your work. Specifically document
all security things, so we can regulate mess which will security generate.

## Creators

- Ondrej Babec (ond.babec@gmail.com, xbabec00)
- Tomas Korbar (tomas.korb@seznam.cz, xkorba02)
- Ladislav Dokoupil ()
