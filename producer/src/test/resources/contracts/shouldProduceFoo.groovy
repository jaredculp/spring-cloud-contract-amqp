import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description('foo')
    label('foo')
    input {
        triggeredBy('foo()')
    }
    outputMessage {
        sentTo('foo')
        headers {
            header('GZIP-Compression', 'true')
        }
        body(fileAsBytes('foo.json.gz'))
    }
}