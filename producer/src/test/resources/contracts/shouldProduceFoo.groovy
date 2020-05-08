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
            messagingContentType(applicationJson())
            header(contentEncoding(), 'gzip:UTF-8')
        }
        body("""{"bar":"baz"}""")
    }
}