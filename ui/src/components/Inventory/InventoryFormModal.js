import Button from '@material-ui/core/Button'
import Checkbox from '@material-ui/core/Checkbox'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import FormControlLabel from '@material-ui/core/FormControlLabel'
import Grid from '@material-ui/core/Grid'
import { MeasurementUnits } from '../../constants/units'
import MenuItem from '@material-ui/core/MenuItem'
import React from 'react'
import TextField from '../Form/TextField'
import { Field, Form, Formik } from 'formik'

class InventoryFormModal extends React.Component {
  render() {
    const {
      formName,
      handleDialog,
      handleInventory,
      title,
      initialValues,
      products,
    } = this.props
    return (
      <Dialog
        open={this.props.isDialogOpen}
        maxWidth='sm'
        fullWidth={true}
        onClose={() => { handleDialog(false) }}
      >
        <Formik
          initialValues={{
            name: '',
            productType: '',
            description: '',
            averagePrice: 0,
            amount: 0,
            unitOfMeasurement: '',
            bestBeforeDate: new Date().toISOString().substring(0, 10),
            neverExpires: false,
            ...initialValues,
          }}
          onSubmit={values => {
            handleInventory({
              ...values,
              // Backend expects a full ISO Instant, not a bare date string.
              bestBeforeDate: new Date(values.bestBeforeDate).toISOString(),
            })
            handleDialog(true)
          }}>
          {helpers =>
            <Form
              noValidate
              autoComplete='off'
              id={formName}
            >
              <DialogTitle id='alert-dialog-title'>
                {`${title} Inventory`}
              </DialogTitle>
              <DialogContent>
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, required: true }}
                      name='name'
                      label='Name'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, required: true, select: true }}
                      name='productType'
                      label='Product Type'
                      component={TextField}
                    >
                      {products.map(product =>
                        <MenuItem key={product.id} value={product.id}>{product.name}</MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true }}
                      name='description'
                      label='Description'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={6} sm={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, type: 'number' }}
                      name='averagePrice'
                      label='Average Price'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={6} sm={6}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, type: 'number' }}
                      name='amount'
                      label='Amount'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, required: true, select: true }}
                      name='unitOfMeasurement'
                      label='Unit of Measurement'
                      component={TextField}
                    >
                      {Object.keys(MeasurementUnits).map(unit =>
                        <MenuItem key={unit} value={unit}>{MeasurementUnits[unit].name}</MenuItem>
                      )}
                    </Field>
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, type: 'date', InputLabelProps: { shrink: true } }}
                      name='bestBeforeDate'
                      label='Best Before Date'
                      component={TextField}
                    />
                  </Grid>
                  <Grid item xs={12} sm={12}>
                    <FormControlLabel
                      control={
                        <Checkbox
                          checked={helpers.values.neverExpires}
                          onChange={helpers.handleChange}
                          name='neverExpires'
                        />
                      }
                      label='Never Expires'
                    />
                  </Grid>
                </Grid>
              </DialogContent>
              <DialogActions>
                <Button onClick={() => { handleDialog(false) }} color='secondary'>Cancel</Button>
                <Button
                  disableElevation
                  variant='contained'
                  type='submit'
                  form={formName}
                  color='secondary'
                  disabled={!helpers.dirty}>
                  Save
                </Button>
              </DialogActions>
            </Form>
          }
        </Formik>
      </Dialog>
    )
  }
}

export default InventoryFormModal