import { Form } from 'react-bootstrap';
import { Controller } from 'react-hook-form';

export default function RadioInput({
                                       name,
                                       id,
                                       control,
                                       className,
                                       containerClass,
                                       label,
                                       helpText,
                                       errors,
                                       value,
                                       ...props
                                   }) {
    return (
        <Controller
            name={name}
            control={control}
            render={({ field, fieldState }) => (
                <Form.Group className={`${containerClass} d-flex gap-1`}>
                    <div className="form-check form-check-inline">
                        <Form.Check
                            type="radio"
                            id={id ?? name}
                            {...props}
                            {...field}
                            value={value}
                            onChange={(e) => {
                                field.onChange(e.target.value);
                            }}
                            isInvalid={Boolean(fieldState.error?.message)}
                        />
                        {label && <Form.Label htmlFor={id ?? name}>{label}</Form.Label>}
                        {helpText && (
                            <Form.Text id={`${name}-help`} muted>
                                {helpText}
                            </Form.Text>
                        )}
                    </div>
                </Form.Group>
            )}
        />
    );
}
