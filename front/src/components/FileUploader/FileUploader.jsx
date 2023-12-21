import { Link } from 'react-router-dom';
import { Row, Col, Card } from 'react-bootstrap';
import Dropzone from 'react-dropzone';
import useFileUploader from './useFileUploader';
import React, { useImperativeHandle } from 'react';

const FileUploader = React.forwardRef(({ showPreview = true, onFileUpload }, ref) => {
  const { selectedFiles, setSelectedFiles, removeFile, formatBytes } = useFileUploader(showPreview);

  useImperativeHandle(ref, () => ({
    getFiles: () => selectedFiles
  }));

  const handleAcceptedFiles = (files) => {
    var allFiles = files.map((file) =>
      Object.assign(file, {
        preview:
          file['type'].split('/')[0] === 'image' ? URL.createObjectURL(file) : null,
        formattedSize: formatBytes(file.size),
      })
    );

    allFiles = [...selectedFiles, ...allFiles];
    setSelectedFiles(allFiles);

    // 선택된 파일들을 상위 컴포넌트로 전달
    if (onFileUpload) onFileUpload(allFiles);
  };

  return (
    <>
      <Dropzone onDrop={handleAcceptedFiles}>
        {({ getRootProps, getInputProps }) => (
          <div className="dropzone">
            <div className="dz-message needsclick" {...getRootProps()}>
              <input {...getInputProps()} />
              <i className="h1 text-muted ri-upload-cloud-2-line"></i>
              <h3>Drop files here or click to upload.</h3>
              <span className="text-muted font-13">
                (This is just a demo dropzone. Selected files are
                <strong>not</strong> actually uploaded.)
              </span>
            </div>
          </div>
        )}
      </Dropzone>

      {showPreview && selectedFiles.length > 0 && (
        <div className="dropzone-previews mt-3" id="uploadPreviewTemplate">
          {(selectedFiles || []).map((f, i) => {
            return (
              <Card className="mt-1 mb-0 shadow-none border" key={i + '-file'}>
                <div className="p-2">
                  <Row className="align-items-center">
                    {f.preview && (
                      <Col className="col-auto">
                        <img
                          data-dz-thumbnail=""
                          className="avatar-sm rounded bg-light"
                          alt={f.name}
                          src={f.preview}
                        />
                      </Col>
                    )}
                    {!f.preview && (
                      <Col className="col-auto">
                        <div className="avatar-sm">
                          <span className="avatar-title bg-primary rounded">
                            {f.type.split('/')[0]}
                          </span>
                        </div>
                      </Col>
                    )}
                    <Col className="ps-0">
                      <Link to="" className="text-muted fw-bold">
                        {f.name}
                      </Link>
                      <p className="mb-0">
                        <strong>{f.formattedSize}</strong>
                      </p>
                    </Col>
                    <Col className="text-end">
                      <Link
                        to=""
                        className="btn btn-link btn-lg text-muted shadow-none"
                      >
                        <i
                          className="ri-close-line"
                          onClick={() => removeFile(f)}
                        ></i>
                      </Link>
                    </Col>
                  </Row>
                </div>
              </Card>
            );
          })}
        </div>
      )}
    </>
  );
});

export { FileUploader };
