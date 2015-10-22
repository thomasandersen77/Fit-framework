package com.github.fit.mongo;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.fit.undertow.HttpUtils;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import lombok.extern.slf4j.Slf4j;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.logging.MorphiaLoggerFactory;
import org.mongodb.morphia.logging.slf4j.SLF4JLoggerImplFactory;

import static java.util.Arrays.*;

@Slf4j
public class EmbeddedMongoRunner {

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private static final int port = HttpUtils.allocatePort();
    private static final String bindIp = "127.0.0.1";
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;
    protected static Datastore datastore;
    private Set<Class> mappedClasses =new HashSet<>();

    public void setMappedClasses(Set<Class> mappedClasses) {
        this.mappedClasses = mappedClasses;
    }

    public EmbeddedMongoRunner mapClasses(Class... classes) {
        this.mappedClasses.addAll(asList(classes));
        return this;
    }

    public static String getMongoAddress() {
        return "mongodb://"+ bindIp +":" + port + "/";
    }

    public void startMongo() {
        try {
            log.debug("Starting embedded MongoDB for [host={}] at [port={}]", bindIp, port);

            _mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(bindIp, port, Network.localhostIsIPv6()))
                .build());

            _mongod = _mongodExe.start();
            final Morphia morphia = new Morphia();
            morphia.getMapper().getConverters().addConverter(new LocalDateTimeConverter());
            morphia.getMapper().getConverters().addConverter(new LocalDateConverter());

            MongoClient client = new MongoClient(bindIp, port);
            datastore = morphia.map(mappedClasses).createDatastore(client, "esoknad-test");
        } catch (IOException e) {
            log.error("Error starting embeddeded MongoDB: {}", e.toString());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void shutdownMongo() {
        log.debug("Shutdown Embedded MongoDB");
        _mongod.stop();
        _mongodExe.stop();
    }
}
