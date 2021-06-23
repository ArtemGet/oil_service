# Environment properties

__Mainly used for app configuration__

## Properties description

```
-DENV={env}
This property will point out the environment in which app is started.
Possible variants: "local_dev"/"production".
If variable not set - local_dev will be set by default.
```

```
-DdataSource={datasource_name}
Currently there are two datasources for this app - postgreSQL and MySQL.
To set up proper datasource set variable to "postgres"/"mysql".
If variable not set - default mysql source will be used.
```

```
-DsecretBuffer={secret_pass}
Will set your token secret buffer.
Error if not set.
```

```
-DkeyStorePath={[your_way_to_jdk]/bin/keystore.jceks}
Will set path to your token keyStore.
Error if not set.
```

```
-DkeyStorePassword={your_key_store_pass}
Error if not set.
```
