function copy_to_clipboard(subjectCodeId) {
    const copyCode = document.getElementById(subjectCodeId).value;

    try {
        const textArea = document.createElement('textarea');
        textArea.value = copyCode;
        document.body.appendChild(textArea);
        textArea.select();
        document.execCommand('copy');
        document.body.removeChild(textArea);
        alert('과목 코드 복사 완료!');
    } catch (error) {
        console.error('클립보드 복사 실패:', error);
    }
}
