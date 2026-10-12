FROM quay.io/keycloak/keycloak:26.7

COPY realm-data/rkt-realm.json /opt/keycloak/data/import/rkt-realm.json
ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
